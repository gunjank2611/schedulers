package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.asset.SFDCEligibleSparesServicesDTO;
import com.thermax.cp.salesforce.dto.asset.SFDCEligibleSparesServicesListDTO;
import com.thermax.cp.salesforce.dto.asset.SFDCOpportunityContactRoleDTOList;
import com.thermax.cp.salesforce.dto.orders.SFDCOpportunityContactRoleDTO;
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
public class OpportunityContactRoleReader implements ItemReader<SFDCOpportunityContactRoleDTO> {

    private String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
    @Autowired
    private SfdcNextRecordsClient sfdcNextRecordsClient;
    private List<SFDCOpportunityContactRoleDTO> sfdcOpportunityContactRoleDTOList;
    private int nextOrderIndex;
    private String frequency;

    public OpportunityContactRoleReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest, String frequency) {
        this.query = QueryConstants.OPPORTUNITY_CONTACT_ROLE;
        this.sfdcBatchDataDetailsRequest = sfdcBatchDataDetailsRequest;
        this.nextOrderIndex = 0;
        this.frequency = frequency;
    }

    @Override
    public SFDCOpportunityContactRoleDTO read() throws Exception {

        if (orderItemsDataNotInitialized()) {
            sfdcOpportunityContactRoleDTOList = getOpportunityContactRoleDetails(query, frequency);
        }
        SFDCOpportunityContactRoleDTO nextOrder;
        if (nextOrderIndex < sfdcOpportunityContactRoleDTOList.size()) {
            nextOrder = sfdcOpportunityContactRoleDTOList.get(nextOrderIndex);
            nextOrderIndex++;
        } else {
            nextOrderIndex = 0;
            nextOrder = null;
        }

        return nextOrder;
    }


    private boolean orderItemsDataNotInitialized() {
        return this.sfdcOpportunityContactRoleDTOList == null;
    }

    private List<SFDCOpportunityContactRoleDTO> getOpportunityContactRoleDetails(String query, String date) throws UnsupportedEncodingException {
        String orderItemsQuery = sfdcServiceUtils.decodeRequestQuery(query) + " " + date + "";
        ResponseEntity<SFDCOpportunityContactRoleDTOList> sfdcOpportunityContactRoleDTOList = sfdcBatchDataDetailsRequest.loadOpportunityContactRole(orderItemsQuery);
        if(sfdcOpportunityContactRoleDTOList!=null) {
            List<SFDCOpportunityContactRoleDTO> contactRoleDTOS = sfdcOpportunityContactRoleDTOList.getBody().getRecords();
            String nextUrl = sfdcOpportunityContactRoleDTOList.getBody().getNextRecordsUrl();

            while (nextUrl != null) {
                try {
                    nextUrl = nextUrl.substring(nextUrl.lastIndexOf('/') + 1);
                    log.info("next url {}", nextUrl);
                    ResponseEntity<SFDCOpportunityContactRoleDTOList> nextRecordsList = sfdcNextRecordsClient.loadOpportunityContactRole(nextUrl);
                    contactRoleDTOS.addAll(nextRecordsList.getBody().getRecords());
                    nextUrl = nextRecordsList.getBody().getNextRecordsUrl();

                } catch (Exception e) {
                    log.info("Error while calling the next records url"+e.getMessage());
                }
            }
            return contactRoleDTOS;
        }
        else
        {
            throw new ResourceNotFoundException("Unable to find contactRoles from SFDC for the specified date : " + date);
        }
    }
}
