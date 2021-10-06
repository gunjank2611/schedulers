package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.asset.SFDCAssetDTO;
import com.thermax.cp.salesforce.dto.asset.SFDCAssetDTOList;
import com.thermax.cp.salesforce.dto.asset.SFDCAssetHistoryDTO;
import com.thermax.cp.salesforce.dto.asset.SFDCAssetHistoryListDTO;
import com.thermax.cp.salesforce.exception.AssetDetailsNotFoundException;
import com.thermax.cp.salesforce.feign.request.SfdcBatchDataDetailsRequest;
import com.thermax.cp.salesforce.query.QueryConstants;
import com.thermax.cp.salesforce.utils.SfdcServiceUtils;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class AssetHistoryReader implements ItemReader<SFDCAssetHistoryDTO> {
    private   String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
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
        ResponseEntity<SFDCAssetHistoryListDTO> assetDetailsList = sfdcBatchDataDetailsRequest.loadAssetHistory(assetHistoryDetailsQuery);
        if (assetDetailsList != null) {
            return assetDetailsList.getBody().getRecords();
        } else {
            throw new AssetDetailsNotFoundException("Unable to find Asset History Details from SFDC for the specified date : " + date);
        }
    }
}
