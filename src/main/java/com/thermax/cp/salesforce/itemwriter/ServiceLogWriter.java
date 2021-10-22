package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.contacts.SFDCContactsDTO;
import com.thermax.cp.salesforce.dto.services.SFDCServiceLogDTO;
import com.thermax.cp.salesforce.dto.services.SFDCServicesDTO;
import com.thermax.cp.salesforce.dto.utils.FileURLDTO;
import com.thermax.cp.salesforce.feign.connectors.AssetsConnector;
import com.thermax.cp.salesforce.utils.CSVWrite;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Log4j2
public class ServiceLogWriter implements ItemWriter<SFDCServiceLogDTO> {

    final private CSVWrite csvWrite;
    final private AssetsConnector assetsConnector;


    public ServiceLogWriter(CSVWrite csvWrite,AssetsConnector assetsConnector)
    {
        this.csvWrite=csvWrite;
        this.assetsConnector=assetsConnector;
    }

    @Override
    public void write(List<? extends SFDCServiceLogDTO> serviceLogDTOS) throws Exception {
        log.info("Received Service Logs from SFDC : {}", serviceLogDTOS.size());
        log.info("Written Service Logs  size : {}", serviceLogDTOS.size());
        final String[] headers = new String[]{"id", "name", "brandedServiceSales", "comments","createdDate","visitDate"};
        final String fileName="services.csv";
        final String apiName="Services";
        CompletableFuture<String> url = csvWrite.writeToCSV(serviceLogDTOS,headers,fileName,apiName);
        log.info("Written assets to the file : {}", url.get());
        FileURLDTO fileURLDTO=new FileURLDTO();
        fileURLDTO.setFileUrl(url.get());
        assetsConnector.sendServiceLogUrl(fileURLDTO);
    }


}
