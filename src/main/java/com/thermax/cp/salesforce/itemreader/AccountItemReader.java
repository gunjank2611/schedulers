package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.account.AccountDetailsListDTO;
import com.thermax.cp.salesforce.dto.account.SFDCAccountInfoDTO;
import com.thermax.cp.salesforce.dto.asset.SFDCAssetDTO;
import com.thermax.cp.salesforce.dto.asset.SFDCAssetDTOList;
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
public class AccountItemReader implements ItemReader<SFDCAccountInfoDTO> {
    private   String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcNextRecordsClient sfdcNextRecordsClient;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
    private List<SFDCAccountInfoDTO> sfdcAccountInfoDTOList;
    private int nextProductIndex;
    private String frequency;

    public AccountItemReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,String frequency)
    {
        this.query= QueryConstants.ACCOUNT_DETAILS_QUERY;
        this.sfdcBatchDataDetailsRequest=sfdcBatchDataDetailsRequest;
        this.nextProductIndex=0;
        this.frequency=frequency;
    }
    @Override
    public SFDCAccountInfoDTO read() throws Exception {

        if(productDataNotInitialized())
        {
            sfdcAccountInfoDTOList =getAccountDetails(query,frequency);
        }
        SFDCAccountInfoDTO nextAccount;
        if (nextProductIndex < sfdcAccountInfoDTOList.size()) {
            nextAccount = sfdcAccountInfoDTOList.get(nextProductIndex);
            nextProductIndex++;
        }
        else {
            nextProductIndex = 0;
            nextAccount = null;
        }

        return nextAccount;
    }

    private boolean productDataNotInitialized()
    {
        return this.sfdcAccountInfoDTOList ==null;
    }

    private List<SFDCAccountInfoDTO> getAccountDetails(String query,String date) throws UnsupportedEncodingException {
        String accountdetailsQuery = sfdcServiceUtils.decodeRequestQuery(query) + " " + date + "";
        ResponseEntity<AccountDetailsListDTO> accountDetailsList = sfdcBatchDataDetailsRequest.loadAccountDetails(accountdetailsQuery);
        if(accountDetailsList!=null) {
            String nextUrl = accountDetailsList.getBody().getNextRecordsUrl();
            List<SFDCAccountInfoDTO> accountsList = accountDetailsList.getBody().getRecords();
            while (nextUrl != null) {
                try {
                    nextUrl = nextUrl.substring(nextUrl.lastIndexOf('/') + 1);
                    log.info("next url {}", nextUrl);
                    ResponseEntity<AccountDetailsListDTO> nextRecordsList = sfdcNextRecordsClient.loadAccountDetails(nextUrl);
                    accountsList.addAll(nextRecordsList.getBody().getRecords());
                    nextUrl = nextRecordsList.getBody().getNextRecordsUrl();

                } catch (Exception e) {
                    log.info("Error while calling the next records url"+e.getMessage());
                }
            }
            return accountsList;
        }
        else
        {
            throw new ResourceNotFoundException("Unable to find Account Details from SFDC for the specified date : " + date);
        }
    }
}
