package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.users.SFDCUserDTOList;
import com.thermax.cp.salesforce.dto.users.SFDCUsersDTO;
import com.thermax.cp.salesforce.dto.users.ThermaxUsersDTO;
import com.thermax.cp.salesforce.dto.users.ThermaxUsersListDTO;
import com.thermax.cp.salesforce.exception.AssetDetailsNotFoundException;
import com.thermax.cp.salesforce.feign.request.SfdcBatchDataDetailsRequest;
import com.thermax.cp.salesforce.query.QueryConstants;
import com.thermax.cp.salesforce.utils.SfdcServiceUtils;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class ThermaxUsersReader implements ItemReader<ThermaxUsersDTO> {
    private   String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
    private List<ThermaxUsersDTO> thermaxUsersDTOList;
    private int nextUserIndex;
    private String frequency;

    public ThermaxUsersReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,String frequency)
    {
        this.query= QueryConstants.THERMAX_USERS_QUERY;
        this.sfdcBatchDataDetailsRequest=sfdcBatchDataDetailsRequest;
        this.nextUserIndex=0;
        this.frequency=frequency;
    }
    @Override
    public ThermaxUsersDTO read() throws Exception {

        if(userDataNotInitialized())
        {
            thermaxUsersDTOList=getThermaxUserDetails(query,frequency);
        }
        ThermaxUsersDTO nextUser;
        if (nextUserIndex < thermaxUsersDTOList.size()) {
            nextUser = thermaxUsersDTOList.get(nextUserIndex);
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
        return this.thermaxUsersDTOList==null;
    }

    private List<ThermaxUsersDTO> getThermaxUserDetails(String query,String date) throws UnsupportedEncodingException {
        String thermaxUsersDetailsQuery = sfdcServiceUtils.decodeRequestQuery(query) + " "+ date + "";
        ResponseEntity<ThermaxUsersListDTO> users = sfdcBatchDataDetailsRequest.loadThermaxUsers(thermaxUsersDetailsQuery);
        if (users != null) {
            return users.getBody().getRecords();
        } else {
            throw new AssetDetailsNotFoundException("Unable to find Thermax User Details from SFDC for the specified date : " + date);
        }
    }
}
