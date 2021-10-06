package com.thermax.cp.salesforce.itemreader;

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
public class ComplaintsReader implements ItemReader<SFDCComplaintsDTO> {
    private   String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
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
        ResponseEntity<SFDCComplaintListDTO> users = sfdcBatchDataDetailsRequest.loadComplaints(productDetailsQuery);
        if (users != null) {
            return users.getBody().getRecords();
        } else {
            throw new AssetDetailsNotFoundException("Unable to find Complaint Details from SFDC for the specified date : " + date);
        }
    }
}
