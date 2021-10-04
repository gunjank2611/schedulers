package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.orders.SFDCOrdersDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOrdersListDTO;
import com.thermax.cp.salesforce.dto.proposals.SFDCProposalsDTO;
import com.thermax.cp.salesforce.dto.proposals.SFDCProposalsListDTO;
import com.thermax.cp.salesforce.exception.AssetDetailsNotFoundException;
import com.thermax.cp.salesforce.feign.request.SfdcBatchDataDetailsRequest;
import com.thermax.cp.salesforce.query.QueryConstants;
import com.thermax.cp.salesforce.utils.SfdcServiceUtils;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class ProposalsReader implements ItemReader<SFDCProposalsDTO> {
    private   String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
    private List<SFDCProposalsDTO> sfdcProposalsDTOList;
    private int nextProposalIndex;

    public ProposalsReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest)
    {
        this.query= QueryConstants.PROPOSALS_QUERY;
        this.sfdcBatchDataDetailsRequest=sfdcBatchDataDetailsRequest;
        this.nextProposalIndex=0;
    }
    @Override
    public SFDCProposalsDTO read() throws Exception {

        if(proposalsDataNotInitialized())
        {
            sfdcProposalsDTOList=getProposalDetails(query,"THIS_WEEK");
        }
        SFDCProposalsDTO nextProposal;
        if (nextProposalIndex < sfdcProposalsDTOList.size()) {
            nextProposal = sfdcProposalsDTOList.get(nextProposalIndex);
            nextProposalIndex++;
        }
        else {
            nextProposalIndex = 0;
            nextProposal = null;
        }

        return nextProposal;
    }

    private boolean proposalsDataNotInitialized()
    {
        return this.sfdcProposalsDTOList==null;
    }

    private List<SFDCProposalsDTO> getProposalDetails(String query,String date) throws UnsupportedEncodingException {
        String proposalsQuery = sfdcServiceUtils.decodeRequestQuery(query) + " "+ date + "";
        ResponseEntity<SFDCProposalsListDTO> proposals = sfdcBatchDataDetailsRequest.loadProposals(proposalsQuery);
        if (proposals != null) {
            return proposals.getBody().getRecords();
        } else {
            throw new AssetDetailsNotFoundException("Unable to find proposals from SFDC for the specified date : " + date);
        }
    }
}
