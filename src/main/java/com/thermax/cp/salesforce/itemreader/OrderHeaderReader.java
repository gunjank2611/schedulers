package com.thermax.cp.salesforce.itemreader;


import com.thermax.cp.salesforce.dto.orders.SFDCOrderHeadersDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOrderHeadersListDTO;
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
public class OrderHeaderReader implements ItemReader<SFDCOrderHeadersDTO> {
    private String query;
    @Autowired
    private SfdcOrdersRequest sfdOrdersRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    private List<SFDCOrderHeadersDTO> SFDCOrderHeadersDTOList;
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
    public SFDCOrderHeadersDTO read() throws Exception {

        if(productDataNotInitialized())
        {
            SFDCOrderHeadersDTOList =getOrderStatusDetails();
        }
        SFDCOrderHeadersDTO nextOrderHeaderDTO;
        if (nextProductIndex < SFDCOrderHeadersDTOList.size()) {
            nextOrderHeaderDTO = SFDCOrderHeadersDTOList.get(nextProductIndex);
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
        return this.SFDCOrderHeadersDTOList ==null;
    }

    private List<SFDCOrderHeadersDTO> getOrderStatusDetails() throws IOException {
        List<OrderIdDTO> orderIds = (List<OrderIdDTO>) CSVRead.readDump(url, "", OrderIdDTO.class);
        ResponseEntity<SFDCOrderHeadersListDTO> orderHeadersDTOList = sfdOrdersRequest.getOrders(orderIds);
        if (orderHeadersDTOList != null) {
            return orderHeadersDTOList.getBody().getOrdersList();
        } else {
            throw new AssetDetailsNotFoundException("Unable to find Order Deatils Details from SFDC");
        }
    }

}
