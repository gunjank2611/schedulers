package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.complaint.SFDCComplaintsDTO;
import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityDTO;
import com.thermax.cp.salesforce.dto.spares.SFDCSparesDTO;
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
public class ComplaintsWriter implements ItemWriter<SFDCComplaintsDTO> {

    final private CSVWrite csvWrite;
    final private EnquiryConnector enquiryConnector;
    public ComplaintsWriter(CSVWrite csvWrite, EnquiryConnector enquiryConnector)
    {
        this.csvWrite=csvWrite;
        this.enquiryConnector=enquiryConnector;
    }
    @Override
    public void write(List<? extends SFDCComplaintsDTO>  complaintsDTOS) throws Exception {
        log.info("Received complaints from SFDC : {}", complaintsDTOS.size());
        log.info("Written complaints size : {}", complaintsDTOS.size());
        final String[] headers = new String[]{"id", "caseNumber", "ownerName", "reason","ownerId","status",
                "subject", "description", "accountId","assetId","contactId","tHS_Division__c","ownerEmail","ownerRoleName","ownerMobilePhone","origin","tHS_Source__c","tHS_Case_Source__c",
        "createdDate","lastModifiedDate"};
        final String fileName="complaints.csv";
        final String apiName="Complaints";
        CompletableFuture<String> url = csvWrite.writeToCSV(complaintsDTOS,headers,fileName,apiName);
        log.info("Written complaints to the file : {}", url.get());
        FileURLDTO fileURLDTO=new FileURLDTO();
        fileURLDTO.setFileUrl(url.get());
        fileURLDTO.setEndPoint("load-complaints");
        fileURLDTO.setFileUploadTimeStamp(ZonedDateTime.now());
        enquiryConnector.sendComplaintsUrl(fileURLDTO);
    }
}
