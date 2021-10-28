package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.pricebook.SFDCPricebookDTO;
import com.thermax.cp.salesforce.dto.pricebook.SFDCPricebookDTOList;
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
public class PricebookReader implements ItemReader<SFDCPricebookDTO> {
    private   String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
    @Autowired
    private SfdcNextRecordsClient sfdcNextRecordsClient;
    private List<SFDCPricebookDTO> sfdcPricebookDTOList;
    private int nextPricebookIndex;
    private String frequency;

    public PricebookReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,String frequency)
    {
        this.query= QueryConstants.PRICEBOOKS_QUERY;
        this.sfdcBatchDataDetailsRequest=sfdcBatchDataDetailsRequest;
        this.nextPricebookIndex=0;
        this.frequency=frequency;
    }
    @Override
    public SFDCPricebookDTO read() throws Exception {

        if(pricebookDataNotInitialized())
        {
            sfdcPricebookDTOList=getPricebookDetails(query,frequency);
        }
        SFDCPricebookDTO nextPricebook;
        if (nextPricebookIndex < sfdcPricebookDTOList.size()) {
            nextPricebook = sfdcPricebookDTOList.get(nextPricebookIndex);
            nextPricebookIndex++;
        }
        else {
            nextPricebookIndex = 0;
            nextPricebook = null;
        }

        return nextPricebook;
    }

    private boolean pricebookDataNotInitialized()
    {
        return this.sfdcPricebookDTOList==null;
    }

    private List<SFDCPricebookDTO> getPricebookDetails(String query,String date) throws UnsupportedEncodingException {
        String pricebookDetailsQuery = sfdcServiceUtils.decodeRequestQuery(query) + " "+ date + "";
        ResponseEntity<SFDCPricebookDTOList> pricebooks = sfdcBatchDataDetailsRequest.loadPricebooks(pricebookDetailsQuery);
        if(pricebooks!=null) {
            List<SFDCPricebookDTO> pricebookDTOS = pricebooks.getBody().getRecords();
            String nextUrl = pricebooks.getBody().getNextRecordsUrl();

            while (nextUrl != null) {
                try {
                    nextUrl = nextUrl.substring(nextUrl.lastIndexOf('/') + 1);
                    log.info("next url {}", nextUrl);
                    ResponseEntity<SFDCPricebookDTOList> nextRecordsList = sfdcNextRecordsClient.loadPricebooks(nextUrl);
                    pricebookDTOS.addAll(nextRecordsList.getBody().getRecords());
                    nextUrl = nextRecordsList.getBody().getNextRecordsUrl();

                } catch (Exception e) {
                    log.info("Error while calling the next records url"+e.getMessage());
                }
            }
            return pricebookDTOS;
        }
        else
        {
            throw new ResourceNotFoundException("Unable to find Pricebooks from SFDC for the specified date : " + date);
        }
    }
}
