package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.orders.SFDCOpportunityContactRoleDTO;
import com.thermax.cp.salesforce.dto.utils.FileURLDTO;
import com.thermax.cp.salesforce.feign.connectors.EnquiryConnector;
import com.thermax.cp.salesforce.utils.CSVWrite;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Log4j2
public class OpportunityContactRoleWriter implements ItemWriter<SFDCOpportunityContactRoleDTO> {

    final private CSVWrite csvWrite;
    final private EnquiryConnector enquiryConnector;


    public OpportunityContactRoleWriter(CSVWrite csvWrite, EnquiryConnector enquiryConnector) {
        this.csvWrite = csvWrite;
        this.enquiryConnector = enquiryConnector;
    }

    @Override
    public void write(List<? extends SFDCOpportunityContactRoleDTO> sfdcOpportunityContactRoleDTOS) throws Exception {
        log.info("Received Opportunity Contact Role from SFDC : {}", sfdcOpportunityContactRoleDTOS.size());
        log.info("Writing Opportunity Contact Role of size : {}", sfdcOpportunityContactRoleDTOS.size());
        final String[] headers = new String[]{"id", "contactId", "isPrimary", "opportunityId", "contactRole"};
        final String fileName = "OpportunityContactRole.csv";
        final String apiName = "OpportunityContactRole";
        CompletableFuture<String> url = csvWrite.writeToCSV(sfdcOpportunityContactRoleDTOS, headers, fileName, apiName);
        log.info("Written Opportunity Contact Role to the file : {}", url.get());
        FileURLDTO fileURLDTO = new FileURLDTO();
        fileURLDTO.setFileUrl(url.get());
        log.info("Pushing Opportunity Contact Role data to DB : {}", fileURLDTO);
        enquiryConnector.sendOpportunityContactRoleBlobUrl(fileURLDTO);
        log.info("Pushed Opportunity Contact Role data to DB !");
    }
}
