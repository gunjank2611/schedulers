package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.orders.SFDCOrderHeadersDTO;
import com.thermax.cp.salesforce.dto.utils.FileURLDTO;
import com.thermax.cp.salesforce.feign.connectors.EnquiryConnector;
import com.thermax.cp.salesforce.utils.CSVWrite;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Log4j2
public class OrderHeaderWriter implements ItemWriter<SFDCOrderHeadersDTO> {
    final private CSVWrite csvWrite;
    final private EnquiryConnector enquiryConnector;


    public OrderHeaderWriter(CSVWrite csvWrite, EnquiryConnector enquiryConnector) {
        this.csvWrite = csvWrite;
        this.enquiryConnector = enquiryConnector;
    }

    @Override
    public void write(List<? extends SFDCOrderHeadersDTO> orderHeaderDTOS) throws Exception {
        log.info("Received order status from SFDC : {}", orderHeaderDTOS.size());
        log.info("Written order status size : {}", orderHeaderDTOS.size());
        final String[] headers = new String[]{"orderNumber", "headerStatus", "expectedDeliveryDate"};
        final String fileName = "orderstatus.csv";
        final String apiName = "OrderStatus";
        CompletableFuture<String> url = csvWrite.writeToCSV(orderHeaderDTOS, headers, fileName, apiName);
        log.info("Written order status to the file : {}", url.get());
        FileURLDTO fileURLDTO = new FileURLDTO();
        fileURLDTO.setFileUrl(url.get());
        enquiryConnector.sendOrderStatusBlobUrl(fileURLDTO);
    }
}
