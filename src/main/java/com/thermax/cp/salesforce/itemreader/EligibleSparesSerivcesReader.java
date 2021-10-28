package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.asset.SFDCEligibleSparesServicesDTO;
import com.thermax.cp.salesforce.dto.asset.SFDCEligibleSparesServicesListDTO;
import com.thermax.cp.salesforce.dto.complaint.SFDCComplaintListDTO;
import com.thermax.cp.salesforce.dto.complaint.SFDCComplaintsDTO;
import com.thermax.cp.salesforce.dto.contacts.SFDCContactsDTO;
import com.thermax.cp.salesforce.dto.contacts.SFDCContactsListDTO;
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
public class EligibleSparesSerivcesReader implements ItemReader<SFDCEligibleSparesServicesDTO> {
    private   String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
    @Autowired
    private SfdcNextRecordsClient sfdcNextRecordsClient;
    private List<SFDCEligibleSparesServicesDTO> sfdcEligibleSparesServicesDTOList;
    private int nextSpareServiceIndex;
    private String frequency;

    public EligibleSparesSerivcesReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,String frequency)
    {
        this.query= QueryConstants.ELIGIBLE_SPARE_SERVICE_QUERY;
        this.sfdcBatchDataDetailsRequest=sfdcBatchDataDetailsRequest;
        this.nextSpareServiceIndex=0;
        this.frequency=frequency;
    }
    @Override
    public SFDCEligibleSparesServicesDTO read() throws Exception {

        if(spareServiceDataNotInitialized())
        {
            sfdcEligibleSparesServicesDTOList=getEligibleSpareServicesDetails(query,frequency);
        }
        SFDCEligibleSparesServicesDTO nextSpareService;
        if (nextSpareServiceIndex < sfdcEligibleSparesServicesDTOList.size()) {
            nextSpareService = sfdcEligibleSparesServicesDTOList.get(nextSpareServiceIndex);
            nextSpareServiceIndex++;
        }
        else {
            nextSpareServiceIndex = 0;
            nextSpareService = null;
        }

        return nextSpareService;
    }

    private boolean spareServiceDataNotInitialized()
    {
        return this.sfdcEligibleSparesServicesDTOList==null;
    }

    private List<SFDCEligibleSparesServicesDTO> getEligibleSpareServicesDetails(String query,String date) throws UnsupportedEncodingException {
        String eligibleSpareServicesQuery = sfdcServiceUtils.decodeRequestQuery(query) + " "+ date + "";
        ResponseEntity<SFDCEligibleSparesServicesListDTO> sparesServices = sfdcBatchDataDetailsRequest.loadEligibleSparesServices(eligibleSpareServicesQuery);
        if(sparesServices!=null) {
            List<SFDCEligibleSparesServicesDTO> eligibleSparesServicesDTOList = sparesServices.getBody().getRecords();
            String nextUrl = sparesServices.getBody().getNextRecordsUrl();

            while (nextUrl != null) {
                try {
                    nextUrl = nextUrl.substring(nextUrl.lastIndexOf('/') + 1);
                    log.info("next url {}", nextUrl);
                    ResponseEntity<SFDCEligibleSparesServicesListDTO> nextRecordsList = sfdcNextRecordsClient.loadEligibleSparesServices(nextUrl);
                    eligibleSparesServicesDTOList.addAll(nextRecordsList.getBody().getRecords());
                    nextUrl = nextRecordsList.getBody().getNextRecordsUrl();

                } catch (Exception e) {
                    log.info("Error while calling the next records url"+e.getMessage());
                }
            }
            return eligibleSparesServicesDTOList;
        }
        else
        {
            throw new ResourceNotFoundException("Unable to find spares and services from SFDC for the specified date : " + date);
        }
    }
}
