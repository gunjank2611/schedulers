package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.complaint.SFDCComplaintsDTO;
import com.thermax.cp.salesforce.dto.contacts.SFDCContactsDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.util.List;
@Log4j2
public class ContactsWriter implements ItemWriter<SFDCContactsDTO> {

    @Override
    public void write(List<? extends SFDCContactsDTO> contactsDTOS) throws Exception {
        log.info("Saving data for contacts of size: {} ", contactsDTOS.size());
    }
}

