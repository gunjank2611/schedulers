package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.asset.SFDCAssetDTO;
import com.thermax.cp.salesforce.dto.asset.SFDCAssetHistoryDTO;
import com.thermax.cp.salesforce.dto.utils.FileURLDTO;
import com.thermax.cp.salesforce.feign.connectors.AssetsConnector;
import com.thermax.cp.salesforce.utils.CSVWrite;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Log4j2
public class AssetHistoryWriter implements ItemWriter<SFDCAssetHistoryDTO> {

    final private CSVWrite csvWrite;
    final private AssetsConnector assetsConnector;

    public AssetHistoryWriter(CSVWrite csvWrite, AssetsConnector assetsConnector)
    {
        this.csvWrite=csvWrite;
        this.assetsConnector=assetsConnector;
    }
    @Override
    public void write(List<? extends SFDCAssetHistoryDTO> assetHistoryDTOS) throws Exception {
        log.info("Received asset history from SFDC : {}", assetHistoryDTOS.size());
        log.info("Written asset history size : {}", assetHistoryDTOS.size());

        final String[] headers = new String[]{"id", "name", "TH_Asset__c","TH_Spare__c","TH_Change_Type__c",
                "TH_Description_for__c","TH_Account_Id__c","LastModifiedDate"};

        final String fileName="AssetHistory.csv";
        final String apiName="AssetHistory";
        CompletableFuture<String> url = csvWrite.writeToCSV(assetHistoryDTOS,headers,fileName,apiName);
        log.info("Written asset history to the file : {}", url.get());
        FileURLDTO fileURLDTO=new FileURLDTO();
        fileURLDTO.setFileUrl(url.get());
        assetsConnector.sendAssetHistoryUrl(fileURLDTO);
    }
}