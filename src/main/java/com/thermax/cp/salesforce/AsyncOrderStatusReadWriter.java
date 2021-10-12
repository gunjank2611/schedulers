package com.thermax.cp.salesforce;

import com.thermax.cp.salesforce.dto.orders.OrderIdDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOrderHeadersDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOrderHeadersListDTO;
import com.thermax.cp.salesforce.dto.utils.FileURLDTO;
import com.thermax.cp.salesforce.exception.AssetDetailsNotFoundException;
import com.thermax.cp.salesforce.feign.connectors.EnquiryConnector;
import com.thermax.cp.salesforce.feign.request.SfdcOrdersRequest;
import com.thermax.cp.salesforce.utils.CSVWrite;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

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

    @Async
    public void fetchWriteOrderStatus(List<OrderIdDTO> orderIds) throws Exception {
        log.info("Requesting order status...");
        ResponseEntity<SFDCOrderHeadersListDTO> orderHeadersDTOList = sfdOrdersRequest.fetchOrderStatus(orderIds);
        log.info("Requested order status response...");
        if (orderHeadersDTOList != null) {
            List<SFDCOrderHeadersDTO> orderStatusList = orderHeadersDTOList.getBody().getOrdersList();
            log.info("Requested order status response...");
            log.info("Found order status for {} items and writing them for DB!", orderStatusList.size());
            processHeaderResponse(orderStatusList);
            log.info("Header response processed!!");
        } else {
            throw new AssetDetailsNotFoundException("Unable to find Order Details from SFDC");
        }
    }


    public void processHeaderResponse(List<SFDCOrderHeadersDTO> orderHeaderDTOS) throws Exception {
        log.info("Received order status from SFDC : {}", orderHeaderDTOS.size());
        final String[] headers = new String[]{"orderNumber", "headerStatus", "expectedDeliveryDate"};
        final String fileName = "orderstatus.csv";
        final String apiName = "OrderStatus";
        CompletableFuture<String> url = csvWrite.writeToCSV(orderHeaderDTOS, headers, fileName, apiName);
        log.info("Written order status to the file : {}", url.get());
        FileURLDTO fileURLDTO = new FileURLDTO();
        fileURLDTO.setFileUrl(url.get());
        log.info("Pushing order status data to DB : {}", fileURLDTO);
        enquiryConnector.sendOrderStatusBlobUrl(fileURLDTO);
        log.info("Pushed order status data to DB !");
    }

}
