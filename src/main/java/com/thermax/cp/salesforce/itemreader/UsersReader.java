package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.pricebook.SFDCPricebookDTO;
import com.thermax.cp.salesforce.dto.pricebook.SFDCPricebookDTOList;
import com.thermax.cp.salesforce.dto.users.SFDCUserDTOList;
import com.thermax.cp.salesforce.dto.users.SFDCUsersDTO;
import com.thermax.cp.salesforce.exception.AssetDetailsNotFoundException;
import com.thermax.cp.salesforce.feign.request.SfdcBatchDataDetailsRequest;
import com.thermax.cp.salesforce.query.QueryConstants;
import com.thermax.cp.salesforce.utils.SfdcServiceUtils;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class UsersReader implements ItemReader<SFDCUsersDTO> {
    private   String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
    private List<SFDCUsersDTO> sfdcUsersDTOSList;
    private int nextUserIndex;

    public UsersReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest)
    {
        this.query= QueryConstants.USERS_QUERY;
        this.sfdcBatchDataDetailsRequest=sfdcBatchDataDetailsRequest;
        this.nextUserIndex=0;
    }
    @Override
    public SFDCUsersDTO read() throws Exception {

        if(userDataNotInitialized())
        {
            sfdcUsersDTOSList=getUserDetails(query,"LAST_MONTH");
        }
        SFDCUsersDTO nextUser;
        if (nextUserIndex < sfdcUsersDTOSList.size()) {
            nextUser = sfdcUsersDTOSList.get(nextUserIndex);
            nextUserIndex++;
        }
        else {
            nextUserIndex = 0;
            nextUser = null;
        }

        return nextUser;
    }

    private boolean userDataNotInitialized()
    {
        return this.sfdcUsersDTOSList==null;
    }

    private List<SFDCUsersDTO> getUserDetails(String query,String date) throws UnsupportedEncodingException {
        String productDetailsQuery = sfdcServiceUtils.decodeRequestQuery(query) + " "+ date + "";
        ResponseEntity<SFDCUserDTOList> users = sfdcBatchDataDetailsRequest.loadUsers(productDetailsQuery);
        if (users != null) {
            return users.getBody().getRecords();
        } else {
            throw new AssetDetailsNotFoundException("Unable to find User Details from SFDC for the specified date : " + date);
        }
    }
}
