package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.contacts.ContactsDTO;
import com.thermax.cp.salesforce.dto.contacts.SFDCContactsDTO;
import com.thermax.cp.salesforce.dto.utils.FileURLDTO;
import com.thermax.cp.salesforce.feign.connectors.ContactsConnector;
import com.thermax.cp.salesforce.mapper.ContactsMapper;
import com.thermax.cp.salesforce.utils.CSVWrite;
import lombok.extern.log4j.Log4j2;
import org.mapstruct.factory.Mappers;
import org.springframework.batch.item.ItemWriter;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Log4j2
public class ContactsWriter implements ItemWriter<SFDCContactsDTO> {

    final private CSVWrite csvWrite;
    final private ContactsConnector contactsConnector;
    private final ContactsMapper contactsMapper = Mappers.getMapper(ContactsMapper.class);

    public ContactsWriter(CSVWrite csvWrite, ContactsConnector contactsConnector) {
        this.csvWrite = csvWrite;
        this.contactsConnector = contactsConnector;
    }

    @Override
    public void write(List<? extends SFDCContactsDTO> sfdcContactsDTO) throws Exception {
        log.info("Saving data for Contacts of size: {} ", sfdcContactsDTO.size());
        log.info("Writing users of size : {}", sfdcContactsDTO.size());
        final String[] headers = new String[]{"id", "firstName", "middleName", "lastName", "accountId", "accountName", "email", "salutation", "department", "designation", "phone", "mobilePhone", "mailingStreet", "mailingCity", "mailingState", "mailingPostalCode", "mailingCountry", "icc", "isActive", "isActiveForCP"};
        final String fileName = "Contacts.csv";
        final String apiName = "Contacts";
        if (sfdcContactsDTO != null && !sfdcContactsDTO.isEmpty()) {
            log.info("Writing response to CSV...");
            List<ContactsDTO> contactsDTOList = contactsMapper.convertToContactsFromSFDCContactsList((List<SFDCContactsDTO>) sfdcContactsDTO);
            CompletableFuture<String> url = csvWrite.writeToCSV(contactsDTOList, headers, fileName, apiName);
            log.info("Written Contacts to the file : {}", url.get());
            FileURLDTO fileURLDTO=new FileURLDTO();
            fileURLDTO.setFileUrl(url.get());
            fileURLDTO.setEndPoint("load-assets");
            fileURLDTO.setFileUploadTimeStamp(ZonedDateTime.now());
            log.info("Pushing data to contacts microservice for consumption and DB persisting...");
            contactsConnector.sendContactsBlobUrl(fileURLDTO);
            log.info("Pushing data process completed!");
        }
    }

}

