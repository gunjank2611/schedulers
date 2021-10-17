package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.pricebook.SFDCPricebookDTO;
import com.thermax.cp.salesforce.dto.pricebook.SFDCPricebookEntryDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Log4j2
public class PricebookEntryWriter implements ItemWriter<SFDCPricebookEntryDTO> {


    @Override
    public void write(List<? extends SFDCPricebookEntryDTO> pricebookEntryDTOS) throws Exception {
        log.info("Saving data for pricebook entrirs of size: {} ", pricebookEntryDTOS.size());
    }
}
