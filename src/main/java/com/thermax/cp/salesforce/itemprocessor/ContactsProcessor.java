package com.thermax.cp.salesforce.itemprocessor;

import com.thermax.cp.salesforce.dto.contacts.SFDCContactsDTO;
import org.springframework.batch.item.ItemProcessor;

public class ContactsProcessor implements ItemProcessor<SFDCContactsDTO,SFDCContactsDTO> {
    @Override
    public SFDCContactsDTO process(SFDCContactsDTO sfdcContactsDTO) throws Exception {

        if(sfdcContactsDTO.getMailingStreet()!=null) {
            String mailingStreet=sfdcContactsDTO.getMailingStreet().replace("\n", "").replace("\r", "")
                    .replaceAll(",","~");;
            sfdcContactsDTO.setMailingStreet(mailingStreet);
        }
        return sfdcContactsDTO;
    }
}
