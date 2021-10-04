package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.pricebook.SFDCPricebookDTO;
import com.thermax.cp.salesforce.dto.pricebook.SFDCPricebookDTOList;
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

public class PricebookReader implements ItemReader<SFDCPricebookDTO> {
    private   String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
    private List<SFDCPricebookDTO> sfdcPricebookDTOList;
    private int nextPricebookIndex;

    public PricebookReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest)
    {
        this.query= QueryConstants.PRICEBOOKS_QUERY;
        this.sfdcBatchDataDetailsRequest=sfdcBatchDataDetailsRequest;
        this.nextPricebookIndex=0;
    }
    @Override
    public SFDCPricebookDTO read() throws Exception {

        if(pricebookDataNotInitialized())
        {
            sfdcPricebookDTOList=getPricebookDetails(query,"LAST_MONTH");
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
        if (pricebooks != null) {
            return pricebooks.getBody().getRecords();
        } else {
            throw new AssetDetailsNotFoundException("Unable to find Pricebook Details from SFDC for the specified date : " + date);
        }
    }
}
