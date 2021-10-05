package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.asset.SFDCAssetDTO;
import com.thermax.cp.salesforce.dto.asset.SFDCAssetDTOList;
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
public class AssetReader implements ItemReader<SFDCAssetDTO> {
    private   String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
    private List<SFDCAssetDTO> sfdcAssetDTOList;
    private int nextProductIndex;
    private String frequency;

    public AssetReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,String frequency)
    {
        this.query= QueryConstants.ASSET_DETAILS_QUERY;
        this.sfdcBatchDataDetailsRequest=sfdcBatchDataDetailsRequest;
        this.nextProductIndex=0;
        this.frequency=frequency;
    }
    @Override
    public SFDCAssetDTO read() throws Exception {

        if(assetDataNotInitialized())
        {
            sfdcAssetDTOList =getAssetDetails(query,frequency);
        }
        SFDCAssetDTO nextAsset;
        if (nextProductIndex < sfdcAssetDTOList.size()) {
            nextAsset = sfdcAssetDTOList.get(nextProductIndex);
            nextProductIndex++;
        }
        else {
            nextProductIndex = 0;
            nextAsset = null;
        }

        return nextAsset;
    }

    private boolean assetDataNotInitialized()
    {
        return this.sfdcAssetDTOList ==null;
    }

    private List<SFDCAssetDTO> getAssetDetails(String query,String date) throws UnsupportedEncodingException {
        String assetDetailsQuery = sfdcServiceUtils.decodeRequestQuery(query) + " " + date + "";
        ResponseEntity<SFDCAssetDTOList> assetDetailsList = sfdcBatchDataDetailsRequest.loadAssets(assetDetailsQuery);
        if (assetDetailsList != null) {
            return assetDetailsList.getBody().getRecords();
        } else {
            throw new AssetDetailsNotFoundException("Unable to find Asset Details from SFDC for the specified date : " + date);
        }
    }
}
