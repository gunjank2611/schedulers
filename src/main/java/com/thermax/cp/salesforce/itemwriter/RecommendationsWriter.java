package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.recommendations.SFDCRecommendationsDTO;
import com.thermax.cp.salesforce.dto.utils.FileURLDTO;
import com.thermax.cp.salesforce.feign.connectors.AssetsConnector;
import com.thermax.cp.salesforce.utils.CSVWrite;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Log4j2
@Transactional
public class RecommendationsWriter implements ItemWriter<SFDCRecommendationsDTO> {

    final private CSVWrite csvWrite;
    final private AssetsConnector assetsConnector;




    public RecommendationsWriter(CSVWrite csvWrite,AssetsConnector assetsConnector)
    {
        this.csvWrite=csvWrite;
        this.assetsConnector=assetsConnector;
    }

    @Override
    public void write(List<? extends SFDCRecommendationsDTO> recommendationsDTOS) throws Exception{
        log.info("Received recommendations from SFDC : {}", recommendationsDTOS.size());
        log.info("Written recommendations size : {}", recommendationsDTOS.size());
        final String[] headers = new String[]{"id", "asset", "accountName", "plannedShutdownDescription", "plannedShutdownDate","status",
                "service", "spare", "selectedServices", "selectedSpares","recommendationType","recommendationSubType","createdDate","lastModifiedDate","partCode"};
        final String fileName="recommendations.csv";
        final String apiName="Recommendations";
        CompletableFuture<String> url = csvWrite.writeToCSV(recommendationsDTOS,headers,fileName,apiName);
        log.info("Written recommendations to the file : {}", url.get());
        FileURLDTO fileURLDTO=new FileURLDTO();
        fileURLDTO.setFileUrl(url.get());
        fileURLDTO.setEndPoint("load-recommendations");
        fileURLDTO.setFileUploadTimeStamp(ZonedDateTime.now());
        assetsConnector.sendRecommendationBlobUrl(fileURLDTO);
    }
}
