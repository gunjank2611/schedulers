package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.pricebook.SFDCPricebookEntryDTO;
import com.thermax.cp.salesforce.dto.pricebook.SFDCPricebookEntryListDTO;
import com.thermax.cp.salesforce.dto.product.ProductListDTO;
import com.thermax.cp.salesforce.dto.product.SFDCProductInfoDTO;
import com.thermax.cp.salesforce.exception.AssetDetailsNotFoundException;
import com.thermax.cp.salesforce.feign.request.SfdcBatchDataDetailsRequest;
import com.thermax.cp.salesforce.query.QueryConstants;
import com.thermax.cp.salesforce.utils.SfdcServiceUtils;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class PricebookEntryReader implements ItemReader<SFDCPricebookEntryDTO> {
    private   String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
    private List<SFDCPricebookEntryDTO> sfdcPricebookEntryDTOList;
    private int nextPricebookEntryIndex;

    public PricebookEntryReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest)
    {
        this.query= QueryConstants.PRICEBOOK_ENTRIES_QUERY;
        this.sfdcBatchDataDetailsRequest=sfdcBatchDataDetailsRequest;
        this.nextPricebookEntryIndex=0;
    }
    @Override
    public SFDCPricebookEntryDTO read() throws Exception {

        if(pricebookEntryDataNotInitialized())
        {
            sfdcPricebookEntryDTOList=getPricebookEntryDetails(query,"LAST_MONTH");
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
        if (pricebookEntries != null) {
            return pricebookEntries.getBody().getRecords();
        } else {
            throw new AssetDetailsNotFoundException("Unable to find Pricebook Entry Details from SFDC for the specified date : " + date);
        }
    }
}
