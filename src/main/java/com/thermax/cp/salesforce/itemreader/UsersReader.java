package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.pricebook.SFDCPricebookDTO;
import com.thermax.cp.salesforce.dto.pricebook.SFDCPricebookDTOList;
import com.thermax.cp.salesforce.dto.users.SFDCUserDTOList;
import com.thermax.cp.salesforce.dto.users.SFDCUsersDTO;
import com.thermax.cp.salesforce.dto.users.ThermaxUsersDTO;
import com.thermax.cp.salesforce.dto.users.ThermaxUsersListDTO;
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
public class UsersReader implements ItemReader<SFDCUsersDTO> {
    private   String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
    @Autowired
    private SfdcNextRecordsClient sfdcNextRecordsClient;
    private List<SFDCUsersDTO> sfdcUsersDTOSList;
    private int nextUserIndex;
    private String frequency;

    public UsersReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,String frequency)
    {
        this.query= QueryConstants.USERS_QUERY;
        this.sfdcBatchDataDetailsRequest=sfdcBatchDataDetailsRequest;
        this.nextUserIndex=0;
        this.frequency=frequency;
    }
    @Override
    public SFDCUsersDTO read() throws Exception {

        if(userDataNotInitialized())
        {
            sfdcUsersDTOSList=getUserDetails(query,frequency);
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
        if(users!=null) {
            List<SFDCUsersDTO> usersDTOS = users.getBody().getRecords();
            String nextUrl = users.getBody().getNextRecordsUrl();

            while (nextUrl != null) {
                try {
                    nextUrl = nextUrl.substring(nextUrl.lastIndexOf('/') + 1);
                    log.info("next url {}", nextUrl);
                    ResponseEntity<SFDCUserDTOList> nextRecordsList = sfdcNextRecordsClient.loadUsers(nextUrl);
                    usersDTOS.addAll(nextRecordsList.getBody().getRecords());
                    nextUrl = nextRecordsList.getBody().getNextRecordsUrl();

                } catch (Exception e) {
                    log.info("Error while calling the next records url"+e.getMessage());
                }
            }
            return usersDTOS;
        }
        else
        {
            throw new ResourceNotFoundException("Unable to find users from SFDC for the specified date : " + date);
        }
    }
}
