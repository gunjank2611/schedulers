package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.complaint.SFDCComplaintsDTO;
import com.thermax.cp.salesforce.dto.opportunity.SFDCOpportunityDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Log4j2
public class ComplaintsWriter implements ItemWriter<SFDCComplaintsDTO> {

    @Override
    public void write(List<? extends SFDCComplaintsDTO> complaintsDTOS) throws Exception {
        log.info("Saving data for complaints of size: {} ", complaintsDTOS.size());
    }
}
