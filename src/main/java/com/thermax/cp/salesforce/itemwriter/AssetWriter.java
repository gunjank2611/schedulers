package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.asset.SFDCAssetDTO;
import com.thermax.cp.salesforce.dto.utils.FileURLDTO;
import com.thermax.cp.salesforce.feign.connectors.AssetsConnector;
import com.thermax.cp.salesforce.utils.CSVWrite;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Log4j2
public class AssetWriter implements ItemWriter<SFDCAssetDTO> {



    final private CSVWrite csvWrite;
    final private AssetsConnector assetsConnector;




    public AssetWriter(CSVWrite csvWrite,AssetsConnector assetsConnector)
    {
        this.csvWrite=csvWrite;
        this.assetsConnector=assetsConnector;
    }

    @Override
    public void write(List<? extends SFDCAssetDTO> assetDTOS) throws Exception {
        log.info("Received assets from SFDC : {}", assetDTOS.size());
        log.info("Written assets size : {}", assetDTOS.size());
        final String[] headers = new String[]{"id", "name", "accountId", "status","division","warrantyExpiredDate",
                "divisionType", "region", "salesOrderNumber", "ownerId","ownerName","ownerUserRoleName","createdDate","lastModifiedDate",
        "installationDate","country","ownerPhoneNumber","contactId"};
        final String fileName="assets.csv";
        final String apiName="Assets";
        CompletableFuture<String> url = csvWrite.writeToCSV(assetDTOS,headers,fileName,apiName);
        log.info("Written assets to the file : {}", url.get());
        FileURLDTO fileURLDTO=new FileURLDTO();
        fileURLDTO.setFileUrl(url.get());
        assetsConnector.sendRecommendationBlobUrl(fileURLDTO);
    }
}
