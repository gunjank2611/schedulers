package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityDTO;
import com.thermax.cp.salesforce.dto.utils.FileURLDTO;
import com.thermax.cp.salesforce.feign.connectors.AssetsConnector;
import com.thermax.cp.salesforce.feign.connectors.EnquiryConnector;
import com.thermax.cp.salesforce.utils.CSVWrite;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Log4j2
public class OpportunityWriter implements ItemWriter<SFDCOpportunityDTO> {

    final private CSVWrite csvWrite;
    final private EnquiryConnector enquiryConnector;

    public OpportunityWriter(CSVWrite csvWrite,EnquiryConnector enquiryConnector) {
        this.csvWrite = csvWrite;
        this.enquiryConnector=enquiryConnector;
    }

    @Override
    public void write(List<? extends SFDCOpportunityDTO> opportunityDTOS) throws Exception {
        log.info("Saving data for Opportunities of size: {}", opportunityDTOS.size());
        final String[] headers = new String[]{"id", "name", "accountId", "closeDate", "stageName", "amount", "tH_Opportunity_Type__c",
                "tH_Customer_Type__c", "tH_Region__c", "createdDate" ,"tHCH_Zone__c", "tHCH_Territory__c", "probability", "tH_Product_Family__c",
                "tHCH_Techno_Commercial_Acceptance_Date__c", "tH_RFQ_Completed__c", "tH_Reason_for_Closed_Lost_Won_Drop__c",
                "cMG_Won_against_Whom__c", "tH_Lost_to_Whom__c", "tH_Closing_Summary__c", "tHCMG_Date_Ordered__c", "tHCMG_Bill_To_Location__c",
                "tHCMG_Ship_To_Location__c", "tHCMG_Cheque_Number__c", "tHCMG_Customer_PO__c", "tHCMG_ERP_Division__c", "tHCMG_Payment_Term__c",
                "tHCMG_Warehouse__c", "tHCMG_Transaction_Type__c", "tHCMG_FOB__c", "tHCMG_Freight_Terms__c", "ownerId","ownerName","ownerEmail","userRoleName","mobilePhone",
                "opportunityNumber","opportunityAssetId"};
        final String fileName = "opportunities.csv";
        final String apiName = "Opportunities";
        CompletableFuture<String> url = csvWrite.writeToCSV(opportunityDTOS, headers, fileName, apiName);
        log.info("Written opportunities to the file : {}", url.get());
        FileURLDTO fileURLDTO=new FileURLDTO();
        fileURLDTO.setFileUrl(url.get());
        fileURLDTO.setEndPoint("load-opportunities");
        fileURLDTO.setFileUploadTimeStamp(ZonedDateTime.now());
        enquiryConnector.sendOpportunities(fileURLDTO);
        log.info("Pushed opportunities data to DB !");
    }
}