package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.services.SFDCServiceLogDTO;
import com.thermax.cp.salesforce.dto.services.SFDCServiceLogListDTO;
import com.thermax.cp.salesforce.dto.services.SFDCServicesDTO;
import com.thermax.cp.salesforce.dto.services.SFDCServicesListDTO;
import com.thermax.cp.salesforce.dto.spares.SFDCSparesDTO;
import com.thermax.cp.salesforce.dto.spares.SFDCSparesListDTO;
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
public class SparesReader implements ItemReader<SFDCSparesDTO> {
    private String query;
    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;
    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;
    @Autowired
    private SfdcNextRecordsClient sfdcNextRecordsClient;
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
        if(sparesListDTOList!=null) {
            List<SFDCSparesDTO> sparesDTOS = sparesListDTOList.getBody().getRecords();
            String nextUrl = sparesListDTOList.getBody().getNextRecordsUrl();

            while (nextUrl != null) {
                try {
                    nextUrl = nextUrl.substring(nextUrl.lastIndexOf('/') + 1);
                    log.info("next url {}", nextUrl);
                    ResponseEntity<SFDCSparesListDTO> nextRecordsList = sfdcNextRecordsClient.loadSpares(nextUrl);
                    sparesDTOS.addAll(nextRecordsList.getBody().getRecords());
                    nextUrl = nextRecordsList.getBody().getNextRecordsUrl();

                } catch (Exception e) {
                    log.info("Error while calling the next records url"+e.getMessage());
                }
            }
            return sparesDTOS;
        }
        else
        {
            throw new ResourceNotFoundException("Unable to find spare details from SFDC for the specified date : " + date);
        }
    }
}
