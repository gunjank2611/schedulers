package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.orders.SFDCOrdersDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOrdersListDTO;
import com.thermax.cp.salesforce.dto.pricebook.SFDCPricebookDTO;
import com.thermax.cp.salesforce.dto.pricebook.SFDCPricebookEntryDTO;
import com.thermax.cp.salesforce.dto.pricebook.SFDCPricebookEntryListDTO;
import com.thermax.cp.salesforce.dto.product.ProductListDTO;
import com.thermax.cp.salesforce.dto.product.SFDCProductInfoDTO;
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
public class PricebookEntryReader implements ItemReader<SFDCPricebookEntryDTO> {
    private   String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
    @Autowired
    private SfdcNextRecordsClient sfdcNextRecordsClient;
    private List<SFDCPricebookEntryDTO> sfdcPricebookEntryDTOList;
    private int nextPricebookEntryIndex;
    private String frequency;

    public PricebookEntryReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,String frequency)
    {
        this.query= QueryConstants.PRICEBOOK_ENTRIES_QUERY;
        this.sfdcBatchDataDetailsRequest=sfdcBatchDataDetailsRequest;
        this.nextPricebookEntryIndex=0;
        this.frequency=frequency;
    }
    @Override
    public SFDCPricebookEntryDTO read() throws Exception {

        if(pricebookEntryDataNotInitialized())
        {
            sfdcPricebookEntryDTOList=getPricebookEntryDetails(query,frequency);
        }
        SFDCPricebookEntryDTO nextPricebookEntry;
        if (nextPricebookEntryIndex < sfdcPricebookEntryDTOList.size()) {
            nextPricebookEntry = sfdcPricebookEntryDTOList.get(nextPricebookEntryIndex);
            nextPricebookEntryIndex++;
        }
        else {
            nextPricebookEntryIndex = 0;
            nextPricebookEntry = null;
        }

        return nextPricebookEntry;
    }

    private boolean pricebookEntryDataNotInitialized()
    {
        return this.sfdcPricebookEntryDTOList==null;
    }

    private List<SFDCPricebookEntryDTO> getPricebookEntryDetails(String query,String date) throws UnsupportedEncodingException {
        String pricebookEntryQuery = sfdcServiceUtils.decodeRequestQuery(query) + " "+ date + "";
        ResponseEntity<SFDCPricebookEntryListDTO> pricebookEntries = sfdcBatchDataDetailsRequest.loadPricebookEntries(pricebookEntryQuery);
        if(pricebookEntries!=null) {
            List<SFDCPricebookEntryDTO> pricebookEntryDTOS = pricebookEntries.getBody().getRecords();
            String nextUrl = pricebookEntries.getBody().getNextRecordsUrl();

            while (nextUrl != null) {
                try {
                    nextUrl = nextUrl.substring(nextUrl.lastIndexOf('/') + 1);
                    log.info("next url {}", nextUrl);
                    ResponseEntity<SFDCPricebookEntryListDTO> nextRecordsList = sfdcNextRecordsClient.loadPricebookEntries(nextUrl);
                    pricebookEntryDTOS.addAll(nextRecordsList.getBody().getRecords());
                    nextUrl = nextRecordsList.getBody().getNextRecordsUrl();

                } catch (Exception e) {
                    log.info("Error while calling the next records url"+e.getMessage());
                }
            }
            return pricebookEntryDTOS;
        }
        else
        {
            throw new ResourceNotFoundException("Unable to find Pricebook Entries from SFDC for the specified date : " + date);
        }
    }
}
