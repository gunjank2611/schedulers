package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.users.SFDCUsersDTO;
import com.thermax.cp.salesforce.dto.users.ThermaxUsersDTO;
import com.thermax.cp.salesforce.dto.utils.FileURLDTO;
import com.thermax.cp.salesforce.feign.connectors.AssetsConnector;
import com.thermax.cp.salesforce.feign.connectors.EnquiryConnector;
import com.thermax.cp.salesforce.utils.CSVWrite;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Log4j2
public class ThermaxUsersWriter implements ItemWriter<ThermaxUsersDTO> {

        final private CSVWrite csvWrite;
        final private EnquiryConnector enquiryConnector;


        public ThermaxUsersWriter(CSVWrite csvWrite, EnquiryConnector enquiryConnector)
        {
                this.csvWrite=csvWrite;
                this.enquiryConnector=enquiryConnector;
        }
@Override
        public void write(List<? extends ThermaxUsersDTO> sfdcUsersDTOS) throws Exception {
        log.info("Received Thermax users from SFDC : {}", sfdcUsersDTOS.size());
        log.info("Written Thermax users size : {}", sfdcUsersDTOS.size());
        final String[] headers = new String[]{"id", "name","userRoleName","email","mobilePhone","title",
                "currencyIsoCode", "managerId","tHCMG_ERP_USER_ID__c","employeeNumber","tHCH_Services__c","tH_IBG_Regions__c","tHCS_Division__c"};
        final String fileName="ThermaxUsers.csv";
        final String apiName="ThermaxUsers";
        CompletableFuture<String> url = csvWrite.writeToCSV(sfdcUsersDTOS,headers,fileName,apiName);
        log.info("Written Thermax users to the file : {}", url.get());
        FileURLDTO fileURLDTO=new FileURLDTO();
        fileURLDTO.setFileUrl(url.get());
        enquiryConnector.sendThermaxUsersUrl(fileURLDTO);
        }
}

