package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.orders.SFDCOrdersDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOrdersListDTO;
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

public class OrderReader implements ItemReader<SFDCOrdersDTO> {
    private   String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
    private List<SFDCOrdersDTO> sfdcOrdersDTOSList;
    private int nextOrderIndex;

    public OrderReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest)
    {
        this.query= QueryConstants.ORDERS_QUERY;
        this.sfdcBatchDataDetailsRequest=sfdcBatchDataDetailsRequest;
        this.nextOrderIndex=0;
    }
    @Override
    public SFDCOrdersDTO read() throws Exception {

        if(ordersDataNotInitialized())
        {
            sfdcOrdersDTOSList=getOrderDetails(query,"THIS_WEEK");
        }
        SFDCOrdersDTO nextOrder;
        if (nextOrderIndex < sfdcOrdersDTOSList.size()) {
            nextOrder = sfdcOrdersDTOSList.get(nextOrderIndex);
            nextOrderIndex++;
        }
        else {
            nextOrderIndex = 0;
            nextOrder = null;
        }

        return nextOrder;
    }

    private boolean ordersDataNotInitialized()
    {
        return this.sfdcOrdersDTOSList==null;
    }

    private List<SFDCOrdersDTO> getOrderDetails(String query,String date) throws UnsupportedEncodingException {
        String orderDetailsQuery = sfdcServiceUtils.decodeRequestQuery(query) + " "+ date + "";
        ResponseEntity<SFDCOrdersListDTO> orders = sfdcBatchDataDetailsRequest.loadOrders(orderDetailsQuery);
        if (orders != null) {
            return orders.getBody().getRecords();
        } else {
            throw new AssetDetailsNotFoundException("Unable to find Order Details from SFDC for the specified date : " + date);
        }
    }
}
