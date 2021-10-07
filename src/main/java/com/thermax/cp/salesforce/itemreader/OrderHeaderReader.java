package com.thermax.cp.salesforce.itemreader;


import com.thermax.cp.salesforce.dto.orders.OrderHeadersDTO;
import com.thermax.cp.salesforce.dto.orders.OrderHeadersListDTO;
import com.thermax.cp.salesforce.dto.orders.OrderIdDTO;
import com.thermax.cp.salesforce.exception.AssetDetailsNotFoundException;
import com.thermax.cp.salesforce.feign.request.SfdcBatchDataDetailsRequest;
import com.thermax.cp.salesforce.feign.request.SfdcOrdersRequest;
import com.thermax.cp.salesforce.utils.CSVRead;
import com.thermax.cp.salesforce.utils.SfdcServiceUtils;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.batch.item.ItemReader;

import java.io.IOException;
import java.util.List;

@StepScope
public class OrderHeaderReader implements ItemReader<OrderHeadersDTO> {
    private String query;
    @Autowired
    private SfdcOrdersRequest sfdOrdersRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    private List<OrderHeadersDTO> orderHeadersDTOList;
    private int nextProductIndex;
    private String url;

    public OrderHeaderReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,String url)
    {
       // this.query= QueryConstants.SERVICES_DETAILS_QUERY;
        this.sfdcBatchDataDetailsRequest=sfdcBatchDataDetailsRequest;
        this.nextProductIndex=0;
        this.url=url;
    }

    @Override
    public OrderHeadersDTO read() throws Exception {

        if(productDataNotInitialized())
        {
            orderHeadersDTOList =getOrderStatusDetails();
        }
        OrderHeadersDTO nextOrderHeaderDTO;
        if (nextProductIndex < orderHeadersDTOList.size()) {
            nextOrderHeaderDTO = orderHeadersDTOList.get(nextProductIndex);
            nextProductIndex++;
        }
        else {
            nextProductIndex = 0;
            nextOrderHeaderDTO = null;
        }

        return nextOrderHeaderDTO;
    }

    private boolean productDataNotInitialized()
    {
        return this.orderHeadersDTOList ==null;
    }

    private List<OrderHeadersDTO> getOrderStatusDetails() throws IOException {
        List<OrderIdDTO> orderIds = (List<OrderIdDTO>) CSVRead.readDump(url, "", OrderIdDTO.class);
        ResponseEntity<OrderHeadersListDTO> orderHeadersDTOList = sfdOrdersRequest.getOrders(orderIds);
        if (orderHeadersDTOList != null) {
            return orderHeadersDTOList.getBody().getOrdersList();
        } else {
            throw new AssetDetailsNotFoundException("Unable to find Order Deatils Details from SFDC");
        }
    }

}
