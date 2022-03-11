package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.orders.SFDCOrderHeadersDTO;
import com.thermax.cp.salesforce.dto.utils.FileURLDTO;
import com.thermax.cp.salesforce.feign.connectors.EnquiryConnector;
import com.thermax.cp.salesforce.utils.CSVWrite;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Log4j2
public class ErpOrderStatusWriter implements ItemWriter<SFDCOrderHeadersDTO> {

    private final CSVWrite csvWrite;
    private final EnquiryConnector enquiryConnector;

    public ErpOrderStatusWriter(CSVWrite csvWrite, EnquiryConnector enquiryConnector) {
        this.csvWrite = csvWrite;
        this.enquiryConnector = enquiryConnector;
    }

    @Override
    public void write(List<? extends SFDCOrderHeadersDTO> orderStatusCompleteList) throws Exception {

        if (!CollectionUtils.isEmpty(orderStatusCompleteList)) {
            log.info("Processing ERP Order Status of size: {} ", orderStatusCompleteList.size());
            try {
                final String[] headers = new String[]{"orderNumber", "headerStatus", "expectedDeliveryDate"};
                final String fileName = "orderstatus.csv";
                final String apiName = "OrderStatus";
                CompletableFuture<String> url = csvWrite.writeToCSV(orderStatusCompleteList, headers, fileName, apiName);
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
        } else {
            log.info("Order status of size: {}, for : {}", orderStatusCompleteList.size());
        }
    }
}
