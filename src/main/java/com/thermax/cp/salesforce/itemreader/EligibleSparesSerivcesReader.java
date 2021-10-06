package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.asset.SFDCEligibleSparesServicesDTO;
import com.thermax.cp.salesforce.dto.asset.SFDCEligibleSparesServicesListDTO;
import com.thermax.cp.salesforce.dto.complaint.SFDCComplaintListDTO;
import com.thermax.cp.salesforce.dto.complaint.SFDCComplaintsDTO;
import com.thermax.cp.salesforce.exception.AssetDetailsNotFoundException;
import com.thermax.cp.salesforce.feign.request.SfdcBatchDataDetailsRequest;
import com.thermax.cp.salesforce.query.QueryConstants;
import com.thermax.cp.salesforce.utils.SfdcServiceUtils;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.util.List;

@StepScope
public class EligibleSparesSerivcesReader implements ItemReader<SFDCEligibleSparesServicesDTO> {
    private   String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
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
        ResponseEntity<SFDCEligibleSparesServicesListDTO> users = sfdcBatchDataDetailsRequest.loadEligibleSparesServices(eligibleSpareServicesQuery);
        if (users != null) {
            return users.getBody().getRecords();
        } else {
            throw new AssetDetailsNotFoundException("Unable to find Eligible spare and services Details from SFDC for the specified date : " + date);
        }
    }
}
