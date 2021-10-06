package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.orders.SFDCOrderItemsDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOrdersDTO;
import com.thermax.cp.salesforce.dto.utils.FileURLDTO;
import com.thermax.cp.salesforce.feign.connectors.AssetsConnector;
import com.thermax.cp.salesforce.utils.CSVWrite;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Log4j2
public class OrderItemsWriter implements ItemWriter<SFDCOrderItemsDTO> {

    final private CSVWrite csvWrite;
    final private AssetsConnector assetsConnector;




    public OrderItemsWriter(CSVWrite csvWrite,AssetsConnector assetsConnector)
    {
        this.csvWrite=csvWrite;
        this.assetsConnector=assetsConnector;
    }

    @Override
    public void write(List<? extends SFDCOrderItemsDTO> orderItemsDTOS) throws Exception {
        log.info("Received Orders from SFDC : {}", orderItemsDTOS.size());
        log.info("Writing orders of size : {}", orderItemsDTOS.size());
        final String[] headers = new String[]{"id", "Product2Id","tH_Product_Code__c",
                "listPrice","unitPrice","totalPrice","tHCMG_ERP_User_Id__c","tH_Asset__c"};
        final String fileName="OrderItems.csv";
        final String apiName="OrderItems";
        CompletableFuture<String> url = csvWrite.writeToCSV(orderItemsDTOS,headers,fileName,apiName);
        log.info("Written orders to the file : {}", url.get());
        FileURLDTO fileURLDTO=new FileURLDTO();
        fileURLDTO.setFileUrl(url.get());
        assetsConnector.sendRecommendationBlobUrl(fileURLDTO);
    }
}
