package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityDTO;
import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityLineItemsDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Log4j2
public class OpportunityLineItemsWriter implements ItemWriter<SFDCOpportunityLineItemsDTO> {
    @Override
    public void write(List<? extends SFDCOpportunityLineItemsDTO> opportunityLineItemsDTOS) throws Exception {
        log.info("Saving data for opportunity line items of size: {} ", opportunityLineItemsDTOS.size());
    }
}
