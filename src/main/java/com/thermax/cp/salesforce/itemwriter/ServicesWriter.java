package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.services.SFDCServicesDTO;
import com.thermax.cp.salesforce.dto.spares.SFDCSparesDTO;
import com.thermax.cp.salesforce.dto.utils.FileURLDTO;
import com.thermax.cp.salesforce.feign.connectors.AssetsConnector;
import com.thermax.cp.salesforce.utils.CSVWrite;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Log4j2
public class ServicesWriter implements ItemWriter<SFDCServicesDTO> {

    final private CSVWrite csvWrite;
    final private AssetsConnector assetsConnector;


    public ServicesWriter(CSVWrite csvWrite,AssetsConnector assetsConnector)
    {
        this.csvWrite=csvWrite;
        this.assetsConnector=assetsConnector;
    }

    @Override
    public void write(List<? extends SFDCServicesDTO> servicesDTOS) throws Exception {
        log.info("Received assets from SFDC : {}", servicesDTOS.size());
        log.info("Written assets size : {}", servicesDTOS.size());
        final String[] headers = new String[]{"id", "name", "countOfVisitLog", "noOfVisits","contractStartDate","contractEndDate",
                "assetId", "region", "division", "executor","salesOrderNumber","quantity","unitSellingPrice","uom","orderedItem","lineTotal",
                "orderLineId","lineStatus","description"};
        final String fileName="services.csv";
        final String apiName="Services";
        CompletableFuture<String> url = csvWrite.writeToCSV(servicesDTOS,headers,fileName,apiName);
        log.info("Written assets to the file : {}", url.get());
        FileURLDTO fileURLDTO=new FileURLDTO();
        fileURLDTO.setFileUrl(url.get());
        assetsConnector.sendServiceUrl(fileURLDTO);
    }


}