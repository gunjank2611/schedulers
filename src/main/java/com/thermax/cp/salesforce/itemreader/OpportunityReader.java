package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.asset.SFDCAssetHistoryDTO;
import com.thermax.cp.salesforce.dto.asset.SFDCAssetHistoryListDTO;
import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityDTO;
import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityDTOList;
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
public class OpportunityReader implements ItemReader<SFDCOpportunityDTO> {
    private   String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
    @Autowired
    private SfdcNextRecordsClient sfdcNextRecordsClient;
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
        if(opportunityDetailList!=null) {
            List<SFDCOpportunityDTO> opportunityDTOS = opportunityDetailList.getBody().getRecords();
            String nextUrl = opportunityDetailList.getBody().getNextRecordsUrl();

            while (nextUrl != null) {
                try {
                    nextUrl = nextUrl.substring(nextUrl.lastIndexOf('/') + 1);
                    log.info("next url {}", nextUrl);
                    ResponseEntity<SFDCOpportunityDTOList> nextRecordsList = sfdcNextRecordsClient.loadOpportunities(nextUrl);
                    opportunityDTOS.addAll(nextRecordsList.getBody().getRecords());
                    nextUrl = nextRecordsList.getBody().getNextRecordsUrl();

                } catch (Exception e) {
                    log.info("Error while calling the next records url"+e.getMessage());
                }
            }
            return opportunityDTOS;
        }
        else
        {
            throw new ResourceNotFoundException("Unable to find opportunityDTOs from SFDC for the specified date : " + date);
        }
    }
}
