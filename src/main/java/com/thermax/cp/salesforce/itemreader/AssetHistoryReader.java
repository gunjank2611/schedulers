package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.asset.SFDCAssetDTO;
import com.thermax.cp.salesforce.dto.asset.SFDCAssetDTOList;
import com.thermax.cp.salesforce.dto.asset.SFDCAssetHistoryDTO;
import com.thermax.cp.salesforce.dto.asset.SFDCAssetHistoryListDTO;
import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityLineItemsDTO;
import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityLineItemsListDTO;
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
@Log4j2
@StepScope
public class AssetHistoryReader implements ItemReader<SFDCAssetHistoryDTO> {
    private   String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
    @Autowired
    private SfdcNextRecordsClient sfdcNextRecordsClient;
    private List<SFDCAssetHistoryDTO> sfdcAssetHistoryDTOList;
    private int nextAssetHistoryIndex;
    private String frequency;

    public AssetHistoryReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,String frequency)
    {
        this.query= QueryConstants.ASSET_HISTORY_QUERY;
        this.sfdcBatchDataDetailsRequest=sfdcBatchDataDetailsRequest;
        this.nextAssetHistoryIndex=0;
        this.frequency=frequency;
    }
    @Override
    public SFDCAssetHistoryDTO read() throws Exception {

        if(assetDataNotInitialized())
        {
            sfdcAssetHistoryDTOList =getAssetHistoryDetails(query,frequency);
        }
        SFDCAssetHistoryDTO nextAssetHistory;
        if (nextAssetHistoryIndex < sfdcAssetHistoryDTOList.size()) {
            nextAssetHistory = sfdcAssetHistoryDTOList.get(nextAssetHistoryIndex);
            nextAssetHistoryIndex++;
        }
        else {
            nextAssetHistoryIndex = 0;
            nextAssetHistory = null;
        }

        return nextAssetHistory;
    }

    private boolean assetDataNotInitialized()
    {
        return this.sfdcAssetHistoryDTOList ==null;
    }

    private List<SFDCAssetHistoryDTO> getAssetHistoryDetails(String query,String date) throws UnsupportedEncodingException {
        String assetHistoryDetailsQuery = sfdcServiceUtils.decodeRequestQuery(query) + " " + date + "";
        ResponseEntity<SFDCAssetHistoryListDTO> assetHistory = sfdcBatchDataDetailsRequest.loadAssetHistory(assetHistoryDetailsQuery);
        if(assetHistory!=null) {
            List<SFDCAssetHistoryDTO> assetHistoryDTOS = assetHistory.getBody().getRecords();
            String nextUrl = assetHistory.getBody().getNextRecordsUrl();

            while (nextUrl != null) {
                try {
                    nextUrl = nextUrl.substring(nextUrl.lastIndexOf('/') + 1);
                    log.info("next url {}", nextUrl);
                    ResponseEntity<SFDCAssetHistoryListDTO> nextRecordsList = sfdcNextRecordsClient.loadAssetHistory(nextUrl);
                    assetHistoryDTOS.addAll(nextRecordsList.getBody().getRecords());
                    nextUrl = nextRecordsList.getBody().getNextRecordsUrl();

                } catch (Exception e) {
                    log.info("Error while calling the next records url"+e.getMessage());
                }
            }
            return assetHistoryDTOS;
        }
        else
        {
            throw new ResourceNotFoundException("Unable to find assetHistoryDTOS from SFDC for the specified date : " + date);
        }
    }
}
