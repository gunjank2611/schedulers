package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.recommendations.SFDCRecommendationsDTO;
import com.thermax.cp.salesforce.dto.recommendations.TCPRecommendationsDTO;
import com.thermax.cp.salesforce.feign.request.SfdcBatchDataDetailsRequest;
import com.thermax.cp.salesforce.service.BatchDataService;
import com.thermax.cp.salesforce.utils.CSVWrite;
import com.thermax.cp.salesforce.mapper.RecommendationsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.mapstruct.factory.Mappers;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class RecommendationsWriter implements ItemWriter<SFDCRecommendationsDTO> {
    private final RecommendationsMapper recommendationsMapper = Mappers.getMapper(RecommendationsMapper.class);

    private CSVWrite csvWrite;

    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;

    @Autowired
    private BatchDataService batchDataService;

    public RecommendationsWriter(CSVWrite csvWrite)
    {
        this.csvWrite=csvWrite;
    }

    @Override
    public void write(List<? extends SFDCRecommendationsDTO> recommendationsDTOS) throws Exception{
        log.info("Received recommendations from SFDC : {}", recommendationsDTOS.size());
        List<TCPRecommendationsDTO> tcpRecommendationsDTOList = new ArrayList<>();
        tcpRecommendationsDTOList = recommendationsDTOS.stream().map(recommendationsMapper::convertToTCPRecommendationDTO).collect(Collectors.toList());
        log.info("Written recommendations size : {}", tcpRecommendationsDTOList.size());
        CompletableFuture<String> url = csvWrite.writeToCSV(tcpRecommendationsDTOList);
    }
}
