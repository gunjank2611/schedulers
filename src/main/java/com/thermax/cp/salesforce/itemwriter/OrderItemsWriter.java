package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.orders.SFDCOrderItemsDTO;
import com.thermax.cp.salesforce.dto.utils.FileURLDTO;
import com.thermax.cp.salesforce.feign.connectors.EnquiryConnector;
import com.thermax.cp.salesforce.utils.CSVWrite;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Log4j2
public class OrderItemsWriter implements ItemWriter<SFDCOrderItemsDTO> {

    final private CSVWrite csvWrite;
    final private EnquiryConnector enquiryConnector;


    public OrderItemsWriter(CSVWrite csvWrite, EnquiryConnector enquiryConnector) {
        this.csvWrite = csvWrite;
        this.enquiryConnector = enquiryConnector;
    }

    @Override
    public void write(List<? extends SFDCOrderItemsDTO> orderItemsDTOS) throws Exception {
        log.info("Received Order Items from SFDC : {}", orderItemsDTOS.size());
        log.info("Writing order Items of size : {}", orderItemsDTOS.size());
        final String[] headers = new String[]{"id", "Product2Id", "tH_Product_Code__c",
                "listPrice", "unitPrice", "totalPrice", "tHCMG_ERP_User_Id__c", "tH_Asset__c"};
        final String fileName = "OrderItems.csv";
        final String apiName = "OrderItems";
        CompletableFuture<String> url = csvWrite.writeToCSV(orderItemsDTOS, headers, fileName, apiName);
        log.info("Written orders Items to the file : {}", url.get());
        FileURLDTO fileURLDTO=new FileURLDTO();
        fileURLDTO.setFileUrl(url.get());
        fileURLDTO.setEndPoint("load-order-items");
        fileURLDTO.setFileUploadTimeStamp(ZonedDateTime.now());
        log.info("Pushing orders Items data to DB : {}", fileURLDTO);
        enquiryConnector.sendOrderItemsBlobUrl(fileURLDTO);
        log.info("Pushed orders Items data to DB !");
    }
}
