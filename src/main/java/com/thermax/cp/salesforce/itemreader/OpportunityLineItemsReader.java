package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.asset.SFDCOpportunityContactRoleDTOList;
import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityDTO;
import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityDTOList;
import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityLineItemsDTO;
import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityLineItemsListDTO;
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
public class OpportunityLineItemsReader implements ItemReader<SFDCOpportunityLineItemsDTO>

    {
        private   String query;
        @Autowired
        private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
        @Autowired
        private SfdcServiceUtils sfdcServiceUtils;
        @Autowired
        private SfdcNextRecordsClient sfdcNextRecordsClient;
        private List<SFDCOpportunityLineItemsDTO> sfdcOpportunityLineItemsDTOList;
        private int nextOpportunityLineItemIndex;
        private String frequency;

    public OpportunityLineItemsReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,String frequency)
        {
            this.query= QueryConstants.OPPORTUNITY_LINE_ITEMS_QUERY;
            this.sfdcBatchDataDetailsRequest=sfdcBatchDataDetailsRequest;
            this.nextOpportunityLineItemIndex=0;
            this.frequency=frequency;
        }
        @Override
        public SFDCOpportunityLineItemsDTO read() throws Exception {

        if(opportunityLineItemDataNotInitialized())
        {
            sfdcOpportunityLineItemsDTOList =getOpportunityLineItemDetails(query,frequency);
        }
        SFDCOpportunityLineItemsDTO nextOpportunityLineItem;
        if (nextOpportunityLineItemIndex < sfdcOpportunityLineItemsDTOList.size()) {
            nextOpportunityLineItem = sfdcOpportunityLineItemsDTOList.get(nextOpportunityLineItemIndex);
            nextOpportunityLineItemIndex++;
        }
        else {
            nextOpportunityLineItemIndex = 0;
            nextOpportunityLineItem = null;
        }

        return nextOpportunityLineItem;
    }

        private boolean opportunityLineItemDataNotInitialized()
        {
            return this.sfdcOpportunityLineItemsDTOList ==null;
        }

        private List<SFDCOpportunityLineItemsDTO> getOpportunityLineItemDetails(String query,String date) throws UnsupportedEncodingException
        {
            String opportunityLineItemsList = sfdcServiceUtils.decodeRequestQuery(query) + " " + date + "";
            ResponseEntity<SFDCOpportunityLineItemsListDTO> opportunityLineItems = sfdcBatchDataDetailsRequest.loadOpportunityLineItems(opportunityLineItemsList);
            if(opportunityLineItems!=null) {
                List<SFDCOpportunityLineItemsDTO> lineItemsDTOList = opportunityLineItems.getBody().getRecords();
                String nextUrl = opportunityLineItems.getBody().getNextRecordsUrl();

                while (nextUrl != null) {
                    try {
                        nextUrl = nextUrl.substring(nextUrl.lastIndexOf('/') + 1);
                        log.info("next url {}", nextUrl);
                        ResponseEntity<SFDCOpportunityLineItemsListDTO> nextRecordsList = sfdcNextRecordsClient.loadOpportunityLineItems(nextUrl);
                        lineItemsDTOList.addAll(nextRecordsList.getBody().getRecords());
                        nextUrl = nextRecordsList.getBody().getNextRecordsUrl();

                    } catch (Exception e) {
                        log.info("Error while calling the next records url"+e.getMessage());
                    }
                }
                return lineItemsDTOList;
            }
            else
            {
                throw new ResourceNotFoundException("Unable to find lineItemsDTO from SFDC for the specified date : " + date);
            }
        }
    }
