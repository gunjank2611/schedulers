package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.product.ProductListDTO;
import com.thermax.cp.salesforce.dto.product.SFDCProductInfoDTO;
import com.thermax.cp.salesforce.dto.services.SFDCServiceLogListDTO;
import com.thermax.cp.salesforce.dto.services.SFDCServiceLogDTO;
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
public class ServiceLogReader implements ItemReader<SFDCServiceLogDTO> {
    private   String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
    @Autowired
    private SfdcNextRecordsClient sfdcNextRecordsClient;
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
        if(serviceLogs!=null) {
            List<SFDCServiceLogDTO> serviceLogDTOS = serviceLogs.getBody().getRecords();
            String nextUrl = serviceLogs.getBody().getNextRecordsUrl();

            while (nextUrl != null) {
                try {
                    nextUrl = nextUrl.substring(nextUrl.lastIndexOf('/') + 1);
                    log.info("next url {}", nextUrl);
                    ResponseEntity<SFDCServiceLogListDTO> nextRecordsList = sfdcNextRecordsClient.loadServiceLog(nextUrl);
                    serviceLogDTOS.addAll(nextRecordsList.getBody().getRecords());
                    nextUrl = nextRecordsList.getBody().getNextRecordsUrl();

                } catch (Exception e) {
                    log.info("Error while calling the next records url"+e.getMessage());
                }
            }
            return serviceLogDTOS;
        }
        else
        {
            throw new ResourceNotFoundException("Unable to find ServiceLog details from SFDC for the specified date : " + date);
        }
    }
}
