package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.asset.SFDCAssetDTO;
import com.thermax.cp.salesforce.dto.services.SFDCServicesDTO;
import com.thermax.cp.salesforce.dto.spares.SFDCSparesDTO;
import com.thermax.cp.salesforce.dto.utils.FileURLDTO;
import com.thermax.cp.salesforce.feign.connectors.AssetsConnector;
import com.thermax.cp.salesforce.utils.CSVWrite;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Log4j2
public class SparesWriter implements ItemWriter<SFDCSparesDTO> {

    final private CSVWrite csvWrite;
    final private AssetsConnector assetsConnector;


    public SparesWriter(CSVWrite csvWrite,AssetsConnector assetsConnector)
    {
        this.csvWrite=csvWrite;
        this.assetsConnector=assetsConnector;
    }

    @Override
    public void write(List<? extends SFDCSparesDTO> sparesDTOS) throws Exception {
        log.info("Received assets from SFDC : {}", sparesDTOS.size());
        log.info("Written assets size : {}", sparesDTOS.size());
        final String[] headers = new String[]{"id", "name", "asset", "region","division","salesOrderNumber",
                "quantity", "unitSellingPrice", "uom", "orderedItem","lineTotal","orderLineId","lineStatus","description","lifecycleDays","account","LastModifiedDate","createdDate","lastUpdatedDate"};
        final String fileName="spares.csv";
        final String apiName="Spares";
        CompletableFuture<String> url = csvWrite.writeToCSV(sparesDTOS,headers,fileName,apiName);
        log.info("Written assets to the file : {}", url.get());
        FileURLDTO fileURLDTO=new FileURLDTO();
        fileURLDTO.setFileUrl(url.get());
        fileURLDTO.setEndPoint("load-spares");
        fileURLDTO.setFileUploadTimeStamp(ZonedDateTime.now());
        assetsConnector.sendSparesUrl(fileURLDTO);
    }
}
