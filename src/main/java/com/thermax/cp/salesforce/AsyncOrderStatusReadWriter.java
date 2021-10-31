package com.thermax.cp.salesforce;

import com.thermax.cp.salesforce.dto.orders.OrderHeadersDTO;
import com.thermax.cp.salesforce.dto.orders.OrderIdDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOrderHeadersDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOrderHeadersListDTO;
import com.thermax.cp.salesforce.dto.utils.FileURLDTO;
import com.thermax.cp.salesforce.exception.AssetDetailsNotFoundException;
import com.thermax.cp.salesforce.feign.connectors.EnquiryConnector;
import com.thermax.cp.salesforce.feign.request.SfdcOrdersRequest;
import com.thermax.cp.salesforce.mapper.OrdersMapper;
import com.thermax.cp.salesforce.utils.CSVWrite;
import com.thermax.cp.salesforce.utils.Partition;
import lombok.extern.log4j.Log4j2;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Log4j2
@Component
public class AsyncOrderStatusReadWriter {
    @Autowired
    private SfdcOrdersRequest sfdOrdersRequest;
    @Autowired
    private CSVWrite csvWrite;
    @Autowired
    private EnquiryConnector enquiryConnector;

    public static Integer ORDER_STATUS_CHUNK_SIZE = 100;
    private final OrdersMapper ordersMapper = Mappers.getMapper(OrdersMapper.class);

    @Async
    public void fetchWriteOrderStatus(List<OrderIdDTO> orderIds, String ordersBlobUrl) throws Exception {
        if (!CollectionUtils.isEmpty(orderIds)) {
            log.info("Requesting order status of size: {}, for : {}", orderIds.size(), ordersBlobUrl);
            Integer count = 0;
            List<SFDCOrderHeadersDTO> orderStatusCompleteList = new ArrayList<>();
            for (List<OrderIdDTO> orderIdDTOS : Partition.ofSize(orderIds, ORDER_STATUS_CHUNK_SIZE)) {
                count = count + orderIdDTOS.size();
                log.info("Requesting order status for next: {} records of total size: {}, processed so far: {}", orderIdDTOS.size(), orderIds.size(), count);
                ResponseEntity<SFDCOrderHeadersListDTO> orderHeadersDTOList = sfdOrdersRequest.fetchOrderStatus(orderIdDTOS);
                log.info("Requested order status response...");
                if (orderHeadersDTOList != null) {
                    List<SFDCOrderHeadersDTO> orderStatusList = orderHeadersDTOList.getBody().getOrdersList();
                    log.info("Found order status for {} records and writing them for DB!", orderStatusList.size());
                    if (orderStatusList.isEmpty()) {
                        log.info("No status found to be updated!");
                    } else {
                        orderStatusCompleteList.addAll(orderStatusList);
                    }
                    log.info("Header response processed!!");
                } else {
                    throw new AssetDetailsNotFoundException("Unable to find Order Details from SFDC");
                }
            }
            if (!CollectionUtils.isEmpty(orderStatusCompleteList)) {
                log.info("Processing order status of size: {}, for : {}", orderStatusCompleteList.size(), ordersBlobUrl);
                processHeaderResponse(orderStatusCompleteList);
            }

        }
    }


    public void processHeaderResponse(List<SFDCOrderHeadersDTO> orderHeaderDTOS) throws Exception {
        log.info("Received order status from SFDC : {}", orderHeaderDTOS.size());
        final String[] headers = new String[]{"orderNumber", "erpStatus", "edd"};
        final String fileName = "orderstatus.csv";
        final String apiName = "OrderStatus";
        List<OrderHeadersDTO> orderHeaderDTOList = ordersMapper.convertToTOrderHeadersDTOList(orderHeaderDTOS);
        CompletableFuture<String> url = csvWrite.writeToCSV(orderHeaderDTOList, headers, fileName, apiName);
        log.info("Written order status to the file : {}", url.get());
        FileURLDTO fileURLDTO = new FileURLDTO();
        fileURLDTO.setFileUrl(url.get());
        log.info("Pushing order status data to DB : {}", fileURLDTO);
        enquiryConnector.sendOrderStatusBlobUrl(fileURLDTO);
        log.info("Pushed order status data to DB !");
    }

}
