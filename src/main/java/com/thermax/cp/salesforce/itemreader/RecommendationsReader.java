package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.recommendations.SFDCRecommendationsDTO;
import com.thermax.cp.salesforce.dto.recommendations.SFDCRecommendationsListDTO;
import com.thermax.cp.salesforce.exception.AssetDetailsNotFoundException;
import com.thermax.cp.salesforce.feign.request.SfdcBatchDataDetailsRequest;
import com.thermax.cp.salesforce.query.QueryConstants;
import com.thermax.cp.salesforce.utils.SfdcServiceUtils;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class RecommendationsReader implements ItemReader<SFDCRecommendationsDTO> {
    private   String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
    private List<SFDCRecommendationsDTO> sfdcRecommendationsDTOList;
    private int nextProductIndex;

    public RecommendationsReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest)
    {
        this.query= QueryConstants.RECOMMENDATIONS_QUERY;
        this.sfdcBatchDataDetailsRequest=sfdcBatchDataDetailsRequest;
        this.nextProductIndex=0;
    }
    @Override
    public SFDCRecommendationsDTO read() throws Exception {

        if(recommendationDataNotInitialized())
        {
            sfdcRecommendationsDTOList =getRecommendationDetails(query,"THIS_WEEK");
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
        ResponseEntity<SFDCRecommendationsListDTO> recommendationsListDTOResponseEntity = sfdcBatchDataDetailsRequest.loadRecommendations(productDetailsQuery);
        if (recommendationsListDTOResponseEntity != null) {
            return recommendationsListDTOResponseEntity.getBody().getRecords();
        } else {
            throw new AssetDetailsNotFoundException("Unable to find Product Details from SFDC for the specified date : " + date);
        }
    }
}
