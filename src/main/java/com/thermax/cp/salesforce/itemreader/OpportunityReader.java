package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityDTO;
import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityDTOList;
import com.thermax.cp.salesforce.exception.AssetDetailsNotFoundException;
import com.thermax.cp.salesforce.feign.request.SfdcBatchDataDetailsRequest;
import com.thermax.cp.salesforce.query.QueryConstants;
import com.thermax.cp.salesforce.utils.SfdcServiceUtils;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.util.List;

@StepScope
public class OpportunityReader implements ItemReader<SFDCOpportunityDTO> {
    private   String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
    private List<SFDCOpportunityDTO> sfdcOpportunityDTOList;
    private int nextProductIndex;
    private String frequency;

    public OpportunityReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,String frequency)
    {
        this.query= QueryConstants.OPPORTUNITIES_QUERY;
        this.sfdcBatchDataDetailsRequest=sfdcBatchDataDetailsRequest;
        this.nextProductIndex=0;
        this.frequency=frequency;
    }
    @Override
    public SFDCOpportunityDTO read() throws Exception {

        if(assetDataNotInitialized())
        {
            sfdcOpportunityDTOList =getOpportunityDetails(query,frequency);
        }
        SFDCOpportunityDTO nextOpportunity;
        if (nextProductIndex < sfdcOpportunityDTOList.size()) {
            nextOpportunity = sfdcOpportunityDTOList.get(nextProductIndex);
            nextProductIndex++;
        }
        else {
            nextProductIndex = 0;
            nextOpportunity = null;
        }

        return nextOpportunity;
    }

    private boolean assetDataNotInitialized()
    {
        return this.sfdcOpportunityDTOList ==null;
    }

    private List<SFDCOpportunityDTO> getOpportunityDetails(String query,String date) throws UnsupportedEncodingException {
        String opportunitiesList = sfdcServiceUtils.decodeRequestQuery(query) + " " + date + "";
        ResponseEntity<SFDCOpportunityDTOList> opportunityDetailList = sfdcBatchDataDetailsRequest.loadOpportunities(opportunitiesList);
        if (opportunityDetailList != null) {
            return opportunityDetailList.getBody().getRecords();
        } else {
            throw new AssetDetailsNotFoundException("Unable to find Asset Details from SFDC for the specified date : " + date);
        }
    }
}
