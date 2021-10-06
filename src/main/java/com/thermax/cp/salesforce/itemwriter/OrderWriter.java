package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.asset.SFDCAssetDTO;
import com.thermax.cp.salesforce.dto.complaint.SFDCComplaintsDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOrdersDTO;
import com.thermax.cp.salesforce.dto.utils.FileURLDTO;
import com.thermax.cp.salesforce.feign.connectors.AssetsConnector;
import com.thermax.cp.salesforce.utils.CSVWrite;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Log4j2
public class OrderWriter implements ItemWriter<SFDCOrdersDTO> {

    final private CSVWrite csvWrite;
    final private AssetsConnector assetsConnector;




    public OrderWriter(CSVWrite csvWrite,AssetsConnector assetsConnector)
    {
        this.csvWrite=csvWrite;
        this.assetsConnector=assetsConnector;
    }

    @Override
    public void write(List<? extends SFDCOrdersDTO> ordersDTOS) throws Exception {
        log.info("Received Orders from SFDC : {}", ordersDTOS.size());
        log.info("Writing orders of size : {}", ordersDTOS.size());
        final String[] headers = new String[]{"id", "orderNumber", "tHCMG_Customer_PO__c", "accountId","tHCMG_Payment_Term__c",
                "tHCMG_ERP_Operating_Unit__c", "tHCMG_Cheque_Number__c", "tHCMG_Transaction_Type_Id__c", "tHCMG_Bill_To_Location__c","tHCMG_Warehouse__c",
                "tHCMG_Ship_To_Location__c","tHCMG_Date_Ordered__c","tHCMG_Payment_Type__c","effectiveDate","totalAmount","tHCMG_FOB__c","tH_Division__c",
                "tHCMG_Freight_Terms__c","status","opportunityId","eRP_Order_Number__c","tH_Opportunity_Number__c","asset__c","ownerId"};
        final String fileName="Orders.csv";
        final String apiName="Orders";
        CompletableFuture<String> url = csvWrite.writeToCSV(ordersDTOS,headers,fileName,apiName);
        log.info("Written orders to the file : {}", url.get());
        FileURLDTO fileURLDTO=new FileURLDTO();
        fileURLDTO.setFileUrl(url.get());
        assetsConnector.sendAssetsBlobUrl(fileURLDTO);
    }
}