package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.services.SFDCServicesDTO;
import com.thermax.cp.salesforce.dto.services.SFDCServicesListDTO;
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
public class ServicesReader implements ItemReader<SFDCServicesDTO> {
    private   String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
    private List<SFDCServicesDTO> sfdcServicesDTOList;
    private int nextProductIndex;
    private String frequency;

    public ServicesReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest,String frequency)
    {
        this.query= QueryConstants.SERVICES_DETAILS_QUERY;
        this.sfdcBatchDataDetailsRequest=sfdcBatchDataDetailsRequest;
        this.nextProductIndex=0;
        this.frequency=frequency;
    }
    @Override
    public SFDCServicesDTO read() throws Exception {

        if(productDataNotInitialized())
        {
            sfdcServicesDTOList =getServicesDetails(query,frequency);
        }
        SFDCServicesDTO nextSpareDTO;
        if (nextProductIndex < sfdcServicesDTOList.size()) {
            nextSpareDTO = sfdcServicesDTOList.get(nextProductIndex);
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
        return this.sfdcServicesDTOList ==null;
    }

    private List<SFDCServicesDTO> getServicesDetails(String query, String date) throws UnsupportedEncodingException {
        String sparesDetailsQuery = sfdcServiceUtils.decodeRequestQuery(query) + " " + date + "";
        ResponseEntity<SFDCServicesListDTO> sparesListDTOList = sfdcBatchDataDetailsRequest.loadServices(sparesDetailsQuery);
        if (sparesListDTOList != null) {
            return sparesListDTOList.getBody().getRecords();
        } else {
            throw new AssetDetailsNotFoundException("Unable to find Spares Deatils Details from SFDC for the specified date : " + date);
        }
    }
}
