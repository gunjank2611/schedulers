package com.thermax.cp.salesforce.itemreader;

import com.thermax.cp.salesforce.dto.contacts.SFDCContactsDTO;
import com.thermax.cp.salesforce.dto.contacts.SFDCContactsListDTO;
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
public class ContactsReader implements ItemReader<SFDCContactsDTO> {

    private String query;

    @Autowired
    private SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest;

    @Autowired
    private SfdcServiceUtils sfdcServiceUtils;

    @Autowired
    private SfdcNextRecordsClient sfdcNextRecordsClient;

    private List<SFDCContactsDTO> sfdcAssetDTOList;
    private int nextContactIndex;
    private String frequency;

    public ContactsReader(SfdcBatchDataDetailsRequest sfdcBatchDataDetailsRequest, String frequency) {
        this.query = QueryConstants.CONTACTS_QUERY;
        this.sfdcBatchDataDetailsRequest = sfdcBatchDataDetailsRequest;
        this.nextContactIndex = 0;
        this.frequency = frequency;
    }

    @Override
    public SFDCContactsDTO read() throws Exception {

        if (contactsDataNotInitialized()) {
            sfdcAssetDTOList = getContactDetails(query, frequency);
        }
        SFDCContactsDTO nextContact;
        if (nextContactIndex < sfdcAssetDTOList.size()) {
            nextContact = sfdcAssetDTOList.get(nextContactIndex);
            nextContactIndex++;
        } else {
            nextContactIndex = 0;
            nextContact = null;
        }

        return nextContact;
    }

    private boolean contactsDataNotInitialized() {
        return this.sfdcAssetDTOList == null;
    }

    private List<SFDCContactsDTO> getContactDetails(String query, String date) throws UnsupportedEncodingException {
        String assetDetailsQuery = sfdcServiceUtils.decodeRequestQuery(query) + " " + date.trim() + "";
        ResponseEntity<SFDCContactsListDTO> contacts = sfdcBatchDataDetailsRequest.loadContacts(assetDetailsQuery);
        if (contacts != null) {
            List<SFDCContactsDTO> contactsList = contacts.getBody().getRecords();
            String nextUrl = contacts.getBody().getNextRecordsUrl();

            while (nextUrl != null) {
                try {
                    nextUrl = nextUrl.substring(nextUrl.lastIndexOf('/') + 1);
                    log.info("next url {}", nextUrl);
                    ResponseEntity<SFDCContactsListDTO> nextRecordsList = sfdcNextRecordsClient.loadContacts(nextUrl);
                    contactsList.addAll(nextRecordsList.getBody().getRecords());
                    nextUrl = nextRecordsList.getBody().getNextRecordsUrl();

                } catch (Exception e) {
                    log.info("Error while calling the next records url" + e.getMessage());
                }
            }
            return contactsList;
        } else {
            throw new ResourceNotFoundException("Unable to find contact Details from SFDC for the specified date : " + date);
        }
    }
}
