package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.orders.SFDCOrdersDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOrdersListDTO;
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
public class OrderReader implements ItemReader<SFDCOrdersDTO> {
    private String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
    @Autowired
    private SfdcNextRecordsClient sfdcNextRecordsClient;
    private List<SFDCOrdersDTO> sfdcOrdersDTOSList;
    private int nextOrderIndex;
    private String frequency;

    public OrderReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest, String frequency) {
        this.query = QueryConstants.ORDERS_QUERY;
        this.sfdcBatchDataDetailsRequest = sfdcBatchDataDetailsRequest;
        this.nextOrderIndex = 0;
        this.frequency = frequency;
    }

    @Override
    public SFDCOrdersDTO read() throws Exception {

        if (ordersDataNotInitialized()) {
            sfdcOrdersDTOSList = getOrderDetails(query, frequency);
        }
        SFDCOrdersDTO nextOrder;
        if (nextOrderIndex < sfdcOrdersDTOSList.size()) {
            nextOrder = sfdcOrdersDTOSList.get(nextOrderIndex);
            nextOrderIndex++;
        } else {
            nextOrderIndex = 0;
            nextOrder = null;
        }

        return nextOrder;
    }

    private boolean ordersDataNotInitialized() {
        return this.sfdcOrdersDTOSList == null;
    }

    private List<SFDCOrdersDTO> getOrderDetails(String query, String date) throws UnsupportedEncodingException {
        String orderDetailsQuery = sfdcServiceUtils.decodeRequestQuery(query) + " " + date + "";
        ResponseEntity<SFDCOrdersListDTO> orders = sfdcBatchDataDetailsRequest.loadOrders(orderDetailsQuery);
        if (orders != null) {
            List<SFDCOrdersDTO> ordersDTOSList = orders.getBody().getRecords();
            String nextUrl = orders.getBody().getNextRecordsUrl();

            while (nextUrl != null) {
                try {
                    nextUrl = nextUrl.substring(nextUrl.lastIndexOf('/') + 1);
                    log.info("next url {}", nextUrl);
                    ResponseEntity<SFDCOrdersListDTO> nextRecordsList = sfdcNextRecordsClient.loadOrders(nextUrl);
                    ordersDTOSList.addAll(nextRecordsList.getBody().getRecords());
                    nextUrl = nextRecordsList.getBody().getNextRecordsUrl();
                } catch (Exception e) {
                    log.info("Error while calling the next records url" + e.getMessage());
                }
            }
            return ordersDTOSList;
        } else {
            throw new ResourceNotFoundException("Unable to find Order Details from SFDC for the specified date : " + date);
        }
    }
}
