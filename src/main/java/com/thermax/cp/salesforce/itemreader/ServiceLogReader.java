package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.services.SFDCServiceLogListDTO;
import com.thermax.cp.salesforce.dto.services.SFDCServiceLogDTO;
import com.thermax.cp.salesforce.exception.AssetDetailsNotFoundException;
import com.thermax.cp.salesforce.feign.request.SfdcBatchDataDetailsRequest;
import com.thermax.cp.salesforce.query.QueryConstants;
import com.thermax.cp.salesforce.utils.SfdcServiceUtils;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class ServiceLogReader implements ItemReader<SFDCServiceLogDTO> {
    private   String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
    private List<SFDCServiceLogDTO> sfdcServiceLogDTOList;
    private int nextServiceLogIndex;
    private String frequency;

    public ServiceLogReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,String frequency)
    {
        this.query= QueryConstants.SERVICE_LOG_QUERY;
        this.sfdcBatchDataDetailsRequest=sfdcBatchDataDetailsRequest;
        this.nextServiceLogIndex=0;
        this.frequency=frequency;
    }
    @Override
    public SFDCServiceLogDTO read() throws Exception {

        if(serviceLogDataNotInitialized())
        {
            sfdcServiceLogDTOList =getServiceLogDetails(query,frequency);
        }
        SFDCServiceLogDTO nextContact;
        if (nextServiceLogIndex < sfdcServiceLogDTOList.size()) {
            nextContact = sfdcServiceLogDTOList.get(nextServiceLogIndex);
            nextServiceLogIndex++;
        }
        else {
            nextServiceLogIndex = 0;
            nextContact = null;
        }

        return nextContact;
    }

    private boolean serviceLogDataNotInitialized()
    {
        return this.sfdcServiceLogDTOList ==null;
    }

    private List<SFDCServiceLogDTO> getServiceLogDetails(String query,String date) throws UnsupportedEncodingException {
        String assetDetailsQuery = sfdcServiceUtils.decodeRequestQuery(query) + " " + date + "";
        ResponseEntity<SFDCServiceLogListDTO> serviceLogs = sfdcBatchDataDetailsRequest.loadServiceLog(assetDetailsQuery);
        if (serviceLogs != null) {
            return serviceLogs.getBody().getRecords();
        } else {
            throw new AssetDetailsNotFoundException("Unable to find service log Details from SFDC for the specified date : " + date);
        }
    }
}
