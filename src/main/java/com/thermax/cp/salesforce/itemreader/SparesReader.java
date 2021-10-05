package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.services.SFDCServicesDTO;
import com.thermax.cp.salesforce.dto.services.SFDCServicesListDTO;
import com.thermax.cp.salesforce.dto.spares.SFDCSparesDTO;
import com.thermax.cp.salesforce.dto.spares.SFDCSparesListDTO;
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
public class SparesReader implements ItemReader<SFDCSparesDTO> {
    private   String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
    private List<SFDCSparesDTO> sfdcSparesDTOList;
    private int nextProductIndex;
    private String frequency;

    public SparesReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,String frequency)
    {
        this.query= QueryConstants.SPARES_DETAILS_QUERY;
        this.sfdcBatchDataDetailsRequest=sfdcBatchDataDetailsRequest;
        this.nextProductIndex=0;
        this.frequency=frequency;
    }
    @Override
    public SFDCSparesDTO read() throws Exception {

        if(productDataNotInitialized())
        {
            sfdcSparesDTOList =getSparesDetails(query,frequency);
        }
        SFDCSparesDTO nextSpareDTO;
        if (nextProductIndex < sfdcSparesDTOList.size()) {
            nextSpareDTO = sfdcSparesDTOList.get(nextProductIndex);
            nextProductIndex++;
        }
        else {
            nextProductIndex = 0;
            nextSpareDTO = null;
        }

        return nextSpareDTO;
    }

    private boolean productDataNotInitialized()
    {
        return this.sfdcSparesDTOList ==null;
    }

    private List<SFDCSparesDTO> getSparesDetails(String query, String date) throws UnsupportedEncodingException {
        String sparesDetailsQuery = sfdcServiceUtils.decodeRequestQuery(query) + " " + date + "";
        ResponseEntity<SFDCSparesListDTO> sparesListDTOList = sfdcBatchDataDetailsRequest.loadSpares(sparesDetailsQuery);
        if (sparesListDTOList != null) {
            return sparesListDTOList.getBody().getRecords();
        } else {
            throw new AssetDetailsNotFoundException("Unable to find Spares Deatils Details from SFDC for the specified date : " + date);
        }
    }
}
