package com.thermax.cp.salesforce;

import com.thermax.cp.salesforce.dto.orders.ErpOrderStatusHeaderListDTO;
import com.thermax.cp.salesforce.dto.orders.OrderHeadersDTO;
import com.thermax.cp.salesforce.dto.orders.PageNumberDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOrderHeadersDTO;
import com.thermax.cp.salesforce.dto.utils.FileURLDTO;
import com.thermax.cp.salesforce.feign.connectors.EnquiryConnector;
import com.thermax.cp.salesforce.feign.request.ErpOrderStatusRequest;
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
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Log4j2
@Component
public class AsyncOrderStatusReadWriter {

    public static Integer ORDER_STATUS_CHUNK_SIZE = 100;
    private final OrdersMapper ordersMapper = Mappers.getMapper(OrdersMapper.class);

    @Autowired
    private ErpOrderStatusRequest sfdOrdersRequest;

    @Autowired
    private CSVWrite csvWrite;

    @Autowired
    private EnquiryConnector enquiryConnector;

    @Async
    public CompletableFuture<List<SFDCOrderHeadersDTO>> fetchWriteOrderStatus(List<PageNumberDTO> orderIds, String ordersBlobUrl) {
        if (!CollectionUtils.isEmpty(orderIds)) {
            log.info("Requesting order status of size: {}, for : {}", orderIds.size(), ordersBlobUrl);
            Integer count = 0;
            List<SFDCOrderHeadersDTO> orderStatusCompleteList = new ArrayList<>();
            for (List<PageNumberDTO> pageNumberDTOS : Partition.ofSize(orderIds, ORDER_STATUS_CHUNK_SIZE)) {
                count = count + pageNumberDTOS.size();
                log.info("Requesting order status for next: {} records of total size: {}, processed so far: {}", pageNumberDTOS.size(), orderIds.size(), count);
                ResponseEntity<ErpOrderStatusHeaderListDTO> orderHeadersDTOList = null;
                try {
                    //orderHeadersDTOList = sfdOrdersRequest.fetchOrderStatus(pageNumberDTOS);
                } catch (Exception e) {
                    log.error("Error while fetching order status! {}", e.getMessage());
                }
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
                    log.info("No order status found!");
                }
            }
            if (!CollectionUtils.isEmpty(orderStatusCompleteList)) {
                log.info("Processing order status of size: {}, for : {}", orderStatusCompleteList.size(), ordersBlobUrl);
                try {
                    final String[] headers = new String[]{"orderNumber", "erpStatus", "edd"};
                    final String fileName = "orderstatus.csv";
                    final String apiName = "OrderStatus";
                    List<OrderHeadersDTO> orderHeaderDTOList = ordersMapper.convertToTOrderHeadersDTOList(orderStatusCompleteList);
                    CompletableFuture<String> url = csvWrite.writeToCSV(orderHeaderDTOList, headers, fileName, apiName);
                    String orderStatusBlobUrl = url.get();
                    if (orderStatusBlobUrl != null) {
                        log.info("Written order status to the file : {}", orderStatusBlobUrl);
                        FileURLDTO fileURLDTO = new FileURLDTO();
                        fileURLDTO.setFileUrl(orderStatusBlobUrl);
                        log.info("Pushing order status data to DB : {}", fileURLDTO);
                        enquiryConnector.sendOrderStatusBlobUrl(fileURLDTO);
                        log.info("Pushed order status data to DB !");
                    } else {
                        log.error("Error while getting order status Blob URL!");
                    }
                } catch (Exception e) {
                    log.error("Error while processing order status: {}", e.getMessage());
                }
            }
            return CompletableFuture.completedFuture(orderStatusCompleteList);
        } else {
            log.info("Order status of size: {}, for : {}", orderIds.size(), ordersBlobUrl);
            return CompletableFuture.completedFuture(Collections.EMPTY_LIST);
        }
    }
}
