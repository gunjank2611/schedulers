package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.account.AccountDetailsListDTO;
import com.thermax.cp.salesforce.dto.account.SFDCAccountInfoDTO;
import com.thermax.cp.salesforce.exception.AssetDetailsNotFoundException;
import com.thermax.cp.salesforce.feign.request.SfdcBatchDataDetailsRequest;
import com.thermax.cp.salesforce.query.QueryConstants;
import com.thermax.cp.salesforce.utils.SfdcServiceUtils;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class AccountItemReader implements ItemReader<SFDCAccountInfoDTO> {
    private   String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
    private List<SFDCAccountInfoDTO> sfdcAccountInfoDTOList;
    private int nextProductIndex;

    public AccountItemReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest)
    {
        this.query= QueryConstants.ACCOUNT_DETAILS_QUERY;
        this.sfdcBatchDataDetailsRequest=sfdcBatchDataDetailsRequest;
        this.nextProductIndex=0;
    }
    @Override
    public SFDCAccountInfoDTO read() throws Exception {

        if(productDataNotInitialized())
        {
            sfdcAccountInfoDTOList =getAccountDetails(query,"LAST_MONTH");
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
        if (accountDetailsList != null) {
            return accountDetailsList.getBody().getRecords();
        } else {
            throw new AssetDetailsNotFoundException("Unable to find Account Details from SFDC for the specified date : " + date);
        }
    }
}
