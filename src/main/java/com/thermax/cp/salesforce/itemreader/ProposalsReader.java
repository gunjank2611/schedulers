package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.orders.SFDCOrdersDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOrdersListDTO;
import com.thermax.cp.salesforce.dto.product.ProductListDTO;
import com.thermax.cp.salesforce.dto.product.SFDCProductInfoDTO;
import com.thermax.cp.salesforce.dto.proposals.SFDCProposalsDTO;
import com.thermax.cp.salesforce.dto.proposals.SFDCProposalsListDTO;
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
public class ProposalsReader implements ItemReader<SFDCProposalsDTO> {
    private   String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
    @Autowired
    private SfdcNextRecordsClient sfdcNextRecordsClient;
    private List<SFDCProposalsDTO> sfdcProposalsDTOList;
    private int nextProposalIndex;
    private String frequency;

    public ProposalsReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,String frequency)
    {
        this.query= QueryConstants.PROPOSALS_QUERY;
        this.sfdcBatchDataDetailsRequest=sfdcBatchDataDetailsRequest;
        this.nextProposalIndex=0;
        this.frequency=frequency;
    }
    @Override
    public SFDCProposalsDTO read() throws Exception {

        if(proposalsDataNotInitialized())
        {
            sfdcProposalsDTOList=getProposalDetails(query,frequency);
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
        if(proposals!=null) {
            List<SFDCProposalsDTO> proposalsDTOS = proposals.getBody().getRecords();
            String nextUrl = proposals.getBody().getNextRecordsUrl();

            while (nextUrl != null) {
                try {
                    nextUrl = nextUrl.substring(nextUrl.lastIndexOf('/') + 1);
                    log.info("next url {}", nextUrl);
                    ResponseEntity<SFDCProposalsListDTO> nextRecordsList = sfdcNextRecordsClient.loadProposals(nextUrl);
                    proposalsDTOS.addAll(nextRecordsList.getBody().getRecords());
                    nextUrl = nextRecordsList.getBody().getNextRecordsUrl();

                } catch (Exception e) {
                    log.info("Error while calling the next records url"+e.getMessage());
                }
            }
            return proposalsDTOS;
        }
        else
        {
            throw new ResourceNotFoundException("Unable to find Proposal details from SFDC for the specified date : " + date);
        }
    }
}
