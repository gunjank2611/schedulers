package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.product.SFDCProductInfoDTO;
import com.thermax.cp.salesforce.dto.proposals.SFDCProposalsDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Log4j2
public class ProposalWriter implements ItemWriter<SFDCProposalsDTO> {
    @Override
    public void write(List<? extends SFDCProposalsDTO> proposalsDTOS) throws Exception {
        log.info("Saving data for products of size: {} ",proposalsDTOS.size());
    }
}
