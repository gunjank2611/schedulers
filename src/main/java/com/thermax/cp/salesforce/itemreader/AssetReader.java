package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.asset.SFDCAssetDTO;
import com.thermax.cp.salesforce.dto.asset.SFDCAssetDTOList;
import com.thermax.cp.salesforce.exception.AssetDetailsNotFoundException;
import com.thermax.cp.salesforce.exception.ResourceNotFoundException;
import com.thermax.cp.salesforce.feign.request.SfdcBatchDataDetailsRequest;
import com.thermax.cp.salesforce.feign.request.SfdcNextRecordsClient;
import com.thermax.cp.salesforce.query.QueryConstants;
import com.thermax.cp.salesforce.utils.SfdcServiceUtils;
import feign.FeignException;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.util.List;

@StepScope
@Log4j2
public class AssetReader implements ItemReader<SFDCAssetDTO> {
    private   String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcNextRecordsClient sfdcNextRecordsClient;
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

    private List<SFDCAssetDTO> getAssetDetails(String query, String date) throws UnsupportedEncodingException {
        String assetDetailsQuery = sfdcServiceUtils.decodeRequestQuery(query) + " " + date + "";
        ResponseEntity<SFDCAssetDTOList> assetDetailsList = sfdcBatchDataDetailsRequest.loadAssets(assetDetailsQuery);
        if(assetDetailsList!=null) {
            List<SFDCAssetDTO> assetsList = assetDetailsList.getBody().getRecords();
            String nextUrl = assetDetailsList.getBody().getNextRecordsUrl();

            while (nextUrl != null) {
                try {
                    nextUrl = nextUrl.substring(nextUrl.lastIndexOf('/') + 1);
                    log.info("next url {}", nextUrl);
                    ResponseEntity<SFDCAssetDTOList> nextRecordsList = sfdcNextRecordsClient.loadAssets(nextUrl);
                    assetsList.addAll(nextRecordsList.getBody().getRecords());
                    nextUrl = nextRecordsList.getBody().getNextRecordsUrl();

                } catch (Exception e) {
                    log.info("Error while calling the next records url"+e.getMessage());
                }
            }
            return assetsList;
        }
        else
        {
            throw new ResourceNotFoundException("Unable to find Asset Details from SFDC for the specified date : " + date);
        }
    }
}
