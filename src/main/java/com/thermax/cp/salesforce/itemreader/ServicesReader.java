package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.orders.SFDCOrdersDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOrdersListDTO;
import com.thermax.cp.salesforce.dto.services.SFDCServicesDTO;
import com.thermax.cp.salesforce.dto.services.SFDCServicesListDTO;
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
public class ServicesReader implements ItemReader<SFDCServicesDTO> {
    private   String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
    @Autowired
    private SfdcNextRecordsClient sfdcNextRecordsClient;
    private List<SFDCServicesDTO> sfdcServicesDTOList;
    private int nextProductIndex;
    private String frequency;

    public ServicesReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,String frequency)
    {
        this.query= QueryConstants.SERVICES_DETAILS_QUERY;
        this.sfdcBatchDataDetailsRequest=sfdcBatchDataDetailsRequest;
        this.nextProductIndex=0;
        this.frequency=frequency;
    }
    @Override
    public SFDCServicesDTO read() throws Exception {

        if(productDataNotInitialized())
        {
            sfdcServicesDTOList =getServicesDetails(query,frequency);
        }
        SFDCServicesDTO nextSpareDTO;
        if (nextProductIndex < sfdcServicesDTOList.size()) {
            nextSpareDTO = sfdcServicesDTOList.get(nextProductIndex);
            nextProductIndex++;
        }
        else {
            nextProductIndex = 0;
            nextSpareDTO = null;
        }

        return nextSpareDTO;
    }

    private boolean productDataNotInitialized()
    {
        return this.sfdcServicesDTOList ==null;
    }

    private List<SFDCServicesDTO> getServicesDetails(String query, String date) throws UnsupportedEncodingException {
        String sparesDetailsQuery = sfdcServiceUtils.decodeRequestQuery(query) + " " + date + "";
        ResponseEntity<SFDCServicesListDTO> services = sfdcBatchDataDetailsRequest.loadServices(sparesDetailsQuery);
        if(services!=null) {
            List<SFDCServicesDTO> sfdcServicesDTOList = services.getBody().getRecords();
            String nextUrl = services.getBody().getNextRecordsUrl();

            while (nextUrl != null) {
                try {
                    nextUrl = nextUrl.substring(nextUrl.lastIndexOf('/') + 1);
                    log.info("next url {}", nextUrl);
                    ResponseEntity<SFDCServicesListDTO> nextRecordsList = sfdcNextRecordsClient.loadServices(nextUrl);
                    sfdcServicesDTOList.addAll(nextRecordsList.getBody().getRecords());
                    nextUrl = nextRecordsList.getBody().getNextRecordsUrl();

                } catch (Exception e) {
                    log.info("Error while calling the next records url"+e.getMessage());
                }
            }
            return sfdcServicesDTOList;
        }
        else
        {
            throw new ResourceNotFoundException("Unable to find Service Details from SFDC for the specified date : " + date);
        }
    }
}
