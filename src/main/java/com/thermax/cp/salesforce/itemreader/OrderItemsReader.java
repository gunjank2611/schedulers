package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityDTO;
import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityDTOList;
import com.thermax.cp.salesforce.dto.orders.SFDCOrderItemsDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOrdersItemsListDTO;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.util.List;

@StepScope
@Log4j2
public class OrderItemsReader implements ItemReader<SFDCOrderItemsDTO> {

    private   String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
    @Autowired
    private SfdcNextRecordsClient sfdcNextRecordsClient;
    private List<SFDCOrderItemsDTO> sfdcOrderItemsDTOList;
    private int nextOrderIndex;
    private String frequency;

    public OrderItemsReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,String frequency)
    {
        this.query= QueryConstants.ORDER_ITEMS_QUERY;
        this.sfdcBatchDataDetailsRequest=sfdcBatchDataDetailsRequest;
        this.nextOrderIndex=0;
        this.frequency=frequency;
    }
    @Override
    public SFDCOrderItemsDTO read() throws Exception {

        if(orderItemsDataNotInitialized())
        {
            sfdcOrderItemsDTOList=getOrderItemDetails(query,frequency);
        }
        SFDCOrderItemsDTO nextOrder;
        if (nextOrderIndex < sfdcOrderItemsDTOList.size()) {
            nextOrder = sfdcOrderItemsDTOList.get(nextOrderIndex);
            nextOrderIndex++;
        }
        else {
            nextOrderIndex = 0;
            nextOrder = null;
        }

        return nextOrder;
    }



    private boolean orderItemsDataNotInitialized()
    {
        return this.sfdcOrderItemsDTOList==null;
    }

    private List<SFDCOrderItemsDTO> getOrderItemDetails(String query,String date) throws UnsupportedEncodingException {
        String orderItemsQuery = sfdcServiceUtils.decodeRequestQuery(query) + " "+ date + "";
        ResponseEntity<SFDCOrdersItemsListDTO> orderItems= sfdcBatchDataDetailsRequest.loadOrderItems(orderItemsQuery);
        if(orderItems!=null) {
            List<SFDCOrderItemsDTO> orderItemsDTOS = orderItems.getBody().getRecords();
            String nextUrl = orderItems.getBody().getNextRecordsUrl();

            while (nextUrl != null) {
                try {
                    nextUrl = nextUrl.substring(nextUrl.lastIndexOf('/') + 1);
                    log.info("next url {}", nextUrl);
                    ResponseEntity<SFDCOrdersItemsListDTO> nextRecordsList = sfdcNextRecordsClient.loadOrderItems(nextUrl);
                    orderItemsDTOS.addAll(nextRecordsList.getBody().getRecords());
                    nextUrl = nextRecordsList.getBody().getNextRecordsUrl();

                } catch (Exception e) {
                    log.info("Error while calling the next records url"+e.getMessage());
                }
            }
            return orderItemsDTOS;
        }
        else
        {
            throw new ResourceNotFoundException("Unable to find orderItemsDTOS from SFDC for the specified date : " + date);
        }
    }
}
