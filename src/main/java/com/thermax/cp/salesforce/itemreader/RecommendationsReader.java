package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.asset.SFDCAssetDTO;
import com.thermax.cp.salesforce.dto.asset.SFDCAssetDTOList;
import com.thermax.cp.salesforce.dto.recommendations.SFDCRecommendationsDTO;
import com.thermax.cp.salesforce.dto.recommendations.SFDCRecommendationsListDTO;
import com.thermax.cp.salesforce.exception.AssetDetailsNotFoundException;
import com.thermax.cp.salesforce.exception.ResourceNotFoundException;
import com.thermax.cp.salesforce.feign.request.SfdcBatchDataDetailsRequest;
import com.thermax.cp.salesforce.feign.request.SfdcNextRecordsClient;
import com.thermax.cp.salesforce.query.QueryConstants;
import com.thermax.cp.salesforce.utils.SfdcServiceUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.util.List;

@StepScope
@Log4j2
public class RecommendationsReader implements ItemReader<SFDCRecommendationsDTO> {
    private   String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
    private List<SFDCRecommendationsDTO> sfdcRecommendationsDTOList;
    @Autowired
    private SfdcNextRecordsClient sfdcNextRecordsClient;
    private int nextProductIndex;
    private String frequency;

    public RecommendationsReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,String frequency)
    {
        this.query= QueryConstants.RECOMMENDATIONS_QUERY;
        this.sfdcBatchDataDetailsRequest=sfdcBatchDataDetailsRequest;
        this.nextProductIndex=0;
        this.frequency=frequency;
    }
    @Override
    public SFDCRecommendationsDTO read() throws Exception {

        if(recommendationDataNotInitialized())
        {
            sfdcRecommendationsDTOList =getRecommendationDetails(query,frequency);
        }
        SFDCRecommendationsDTO sfdcRecommendationsDTO;
        if (nextProductIndex < sfdcRecommendationsDTOList.size()) {
            sfdcRecommendationsDTO = sfdcRecommendationsDTOList.get(nextProductIndex);
            nextProductIndex++;
        }
        else {
            nextProductIndex = 0;
            sfdcRecommendationsDTO = null;
        }

        return sfdcRecommendationsDTO;
    }

    private boolean recommendationDataNotInitialized()
    {
        return this.sfdcRecommendationsDTOList ==null;
    }

    private List<SFDCRecommendationsDTO> getRecommendationDetails(String query, String date) throws UnsupportedEncodingException {
        String productDetailsQuery = sfdcServiceUtils.decodeRequestQuery(query) + " "+ date + "";
        ResponseEntity<SFDCRecommendationsListDTO> recommendationsList = sfdcBatchDataDetailsRequest.loadRecommendations(productDetailsQuery);
        if(recommendationsList!=null) {
            List<SFDCRecommendationsDTO> recommendationsDTOList = recommendationsList.getBody().getRecords();
            String nextUrl = recommendationsList.getBody().getNextRecordsUrl();

            while (nextUrl != null) {
                try {
                    nextUrl = nextUrl.substring(nextUrl.lastIndexOf('/') + 1);
                    log.info("next url {}", nextUrl);
                    ResponseEntity<SFDCRecommendationsListDTO> nextRecordsList = sfdcNextRecordsClient.loadRecommendations(nextUrl);
                    recommendationsDTOList.addAll(nextRecordsList.getBody().getRecords());
                    nextUrl = nextRecordsList.getBody().getNextRecordsUrl();

                } catch (Exception e) {
                    log.info("Error while calling the next records url"+e.getMessage());
                }
            }
            return recommendationsDTOList;
        }
        else
        {
            throw new ResourceNotFoundException("Unable to find Recommendations Details from SFDC for the specified date : " + date);
        }
    }
}
