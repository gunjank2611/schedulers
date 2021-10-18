package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.proposals.SFDCProposalsDTO;
import com.thermax.cp.salesforce.dto.utils.FileURLDTO;
import com.thermax.cp.salesforce.utils.CSVWrite;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Log4j2
public class ProposalWriter implements ItemWriter<SFDCProposalsDTO> {

    final private CSVWrite csvWrite;

    public ProposalWriter(CSVWrite csvWrite) {
        this.csvWrite = csvWrite;
    }


    @Override
    public void write(List<? extends SFDCProposalsDTO> proposalsDTOS) throws Exception {
        log.info("Saving data for proposals of size: {} ", proposalsDTOS.size());
        final String[] headers = new String[]{"id", "name", "account__c", "additional_Input_By_Proposer__c", "additional_Input_By_Requester__c", "asset__c", "committed_Date__c", "createdDate", "department__c", "latestVersion__c", "revisedAfterClosure__c", "lastModifiedDate", "opportunity__c", "original_Proposal__c", "proposal_Number__c", "proposer_User__c", "reason__c", "status__c"};
        final String fileName = "proposals.csv";
        final String apiName = "Proposals";
        CompletableFuture<String> url = csvWrite.writeToCSV(proposalsDTOS, headers, fileName, apiName);
        log.info("Written proposals to the file : {}", url.get());
        FileURLDTO fileURLDTO = new FileURLDTO();
        fileURLDTO.setFileUrl(url.get());
    }
}
