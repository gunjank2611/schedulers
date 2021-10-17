package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Log4j2
public class OpportunityWriter implements ItemWriter<SFDCOpportunityDTO> {
    @Override
    public void write(List<? extends SFDCOpportunityDTO> opportunityDTOS) throws Exception {
        log.info("Saving data for assets of size:", opportunityDTOS.size());
    }
}