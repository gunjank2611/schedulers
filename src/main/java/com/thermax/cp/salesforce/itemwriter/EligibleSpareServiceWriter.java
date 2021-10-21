package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.asset.SFDCEligibleSparesServicesDTO;
import com.thermax.cp.salesforce.dto.utils.FileURLDTO;
import com.thermax.cp.salesforce.feign.connectors.EnquiryConnector;
import com.thermax.cp.salesforce.utils.CSVWrite;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Log4j2
public class EligibleSpareServiceWriter implements ItemWriter<SFDCEligibleSparesServicesDTO> {

    private final CSVWrite csvWrite;
    private final EnquiryConnector enquiryConnector;

    public EligibleSpareServiceWriter(CSVWrite csvWrite, EnquiryConnector enquiryConnector) {
        this.csvWrite = csvWrite;
        this.enquiryConnector = enquiryConnector;
    }

    @Override
    public void write(List<? extends SFDCEligibleSparesServicesDTO> eligibleSparesServicesDTOS) throws Exception {
        log.info("Saving data for eligible spares and services of size: {} ", eligibleSparesServicesDTOS.size());
         String[] headers = new String[]{"id", "asset__c", "currencyIsoCode", "life_Cycle_Date__c", "name", "part_Number__c","tH_Thermax_Spare__c",
                "type__c", "when_to_Engage_Customer__c"};
        final String fileName="eligibleSparesServices.csv";
        final String apiName="EligibleSparesServices";
        CompletableFuture<String> url = csvWrite.writeToCSV(eligibleSparesServicesDTOS,headers,fileName,apiName);
        log.info("Written Eligible Spares Services to the file : {}", url.get());
        FileURLDTO fileURLDTO=new FileURLDTO();
        fileURLDTO.setFileUrl(url.get());
        enquiryConnector.sendEligibleSparesServiceUrl(fileURLDTO);
        log.info("Pushed Eligible Spares Service data to DB !");
    }
}
