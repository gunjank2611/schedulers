package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityDTO;
import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityLineItemsDTO;
import com.thermax.cp.salesforce.dto.utils.FileURLDTO;
import com.thermax.cp.salesforce.feign.connectors.EnquiryConnector;
import com.thermax.cp.salesforce.utils.CSVWrite;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Log4j2
public class OpportunityLineItemsWriter implements ItemWriter<SFDCOpportunityLineItemsDTO> {

    final private CSVWrite csvWrite;

    final private EnquiryConnector enquiryConnector;

    public OpportunityLineItemsWriter(CSVWrite csvWrite,EnquiryConnector enquiryConnector) {
        this.csvWrite = csvWrite;
        this.enquiryConnector=enquiryConnector;
    }
    @Override
    public void write(List<? extends SFDCOpportunityLineItemsDTO> opportunityLineItemsDTOS) throws Exception {
        log.info("Saving data for opportunity line items of size: {} ", opportunityLineItemsDTOS.size());
        final String[] headers = new String[]{"id", "opportunityId", "product2Id", "tH_Product_Family__c",
        "tH_Forecast_Category_Name__c","tH_ENV_Enviro_Quantity__c","productCode","listPrice","unitPrice","quantity","tHCMG_Product_Name__c","tH_CNH_Division__c"};
        final String fileName = "opportunityLineItems.csv";
        final String apiName = "OpportunityLineItems";
        CompletableFuture<String> url = csvWrite.writeToCSV(opportunityLineItemsDTOS, headers, fileName, apiName);
        log.info("Written opportunity line items to the file : {}", url.get());
        FileURLDTO fileURLDTO = new FileURLDTO();
        fileURLDTO.setFileUrl(url.get());
        enquiryConnector.sendOpportunityLineItems(fileURLDTO);
        log.info("Pushed opportunity line items data to DB !");
    }
}
