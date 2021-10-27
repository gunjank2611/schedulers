package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.asset.SFDCAssetDTO;
import com.thermax.cp.salesforce.dto.asset.SFDCAssetDTOList;
import com.thermax.cp.salesforce.dto.complaint.SFDCComplaintListDTO;
import com.thermax.cp.salesforce.dto.complaint.SFDCComplaintsDTO;
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
public class ComplaintsReader implements ItemReader<SFDCComplaintsDTO> {
    private   String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
    @Autowired
    private SfdcNextRecordsClient sfdcNextRecordsClient;

    private List<SFDCComplaintsDTO> sfdcComplaintsDTOList;
    private int nextComplaintIndex;
    private String frequency;

    public ComplaintsReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,String frequency)
    {
        this.query= QueryConstants.COMPLAINTS_QUERY;
        this.sfdcBatchDataDetailsRequest=sfdcBatchDataDetailsRequest;
        this.nextComplaintIndex=0;
        this.frequency=frequency;
    }
    @Override
    public SFDCComplaintsDTO read() throws Exception {

        if(complaintsDataNotInitialized())
        {
            sfdcComplaintsDTOList=getComplaintDetails(query,frequency);
        }
        SFDCComplaintsDTO nextComplaint;
        if (nextComplaintIndex < sfdcComplaintsDTOList.size()) {
            nextComplaint = sfdcComplaintsDTOList.get(nextComplaintIndex);
            nextComplaintIndex++;
        }
        else {
            nextComplaintIndex = 0;
            nextComplaint = null;
        }

        return nextComplaint;
    }

    private boolean complaintsDataNotInitialized()
    {
        return this.sfdcComplaintsDTOList==null;
    }

    private List<SFDCComplaintsDTO> getComplaintDetails(String query,String date) throws UnsupportedEncodingException {
        String productDetailsQuery = sfdcServiceUtils.decodeRequestQuery(query) + " "+ date + "";
        ResponseEntity<SFDCComplaintListDTO> complaints = sfdcBatchDataDetailsRequest.loadComplaints(productDetailsQuery);
        if(complaints!=null) {
            List<SFDCComplaintsDTO> complaintsList = complaints.getBody().getRecords();
            String nextUrl = complaints.getBody().getNextRecordsUrl();

            while (nextUrl != null) {
                try {
                    nextUrl = nextUrl.substring(nextUrl.lastIndexOf('/') + 1);
                    log.info("next url {}", nextUrl);
                    ResponseEntity<SFDCComplaintListDTO> nextRecordsList = sfdcNextRecordsClient.loadComplaints(nextUrl);
                    complaintsList.addAll(nextRecordsList.getBody().getRecords());
                    nextUrl = nextRecordsList.getBody().getNextRecordsUrl();

                } catch (Exception e) {
                    log.info("Error while calling the next records url"+e.getMessage());
                }
            }
            return complaintsList;
        }
        else
        {
            throw new ResourceNotFoundException("Unable to find Complaint Details from SFDC for the specified date : " + date);
        }
    }
}
