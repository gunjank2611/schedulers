package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityDTO;
import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityDTOList;
import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityLineItemsDTO;
import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityLineItemsListDTO;
import com.thermax.cp.salesforce.exception.AssetDetailsNotFoundException;
import com.thermax.cp.salesforce.feign.request.SfdcBatchDataDetailsRequest;
import com.thermax.cp.salesforce.query.QueryConstants;
import com.thermax.cp.salesforce.utils.SfdcServiceUtils;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class OpportunityLineItemsReader implements ItemReader<SFDCOpportunityLineItemsDTO>

    {
        private   String query;
        @Autowired
        private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
        @Autowired
        private SfdcServiceUtils sfdcServiceUtils;
        private List<SFDCOpportunityLineItemsDTO> sfdcOpportunityLineItemsDTOList;
        private int nextOpportunityLineItemIndex;

    public OpportunityLineItemsReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest)
        {
            this.query= QueryConstants.OPPORTUNITY_LINE_ITEMS_QUERY;
            this.sfdcBatchDataDetailsRequest=sfdcBatchDataDetailsRequest;
            this.nextOpportunityLineItemIndex=0;
        }
        @Override
        public SFDCOpportunityLineItemsDTO read() throws Exception {

        if(opportunityLineItemDataNotInitialized())
        {
            sfdcOpportunityLineItemsDTOList =getOpportunityLineItemDetails(query,"THIS_WEEK");
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
            if (opportunityLineItems != null) {
                return opportunityLineItems.getBody().getRecords();
            } else {
                throw new AssetDetailsNotFoundException("Unable to find Opportunity Line item Details from SFDC for the specified date : " + date);
            }
        }
    }
