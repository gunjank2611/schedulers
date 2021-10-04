package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.services.SFDCServicesDTO;
import com.thermax.cp.salesforce.dto.spares.SFDCSparesDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.util.List;
@Log4j2
public class SparesWriter implements ItemWriter<SFDCSparesDTO> {
    @Override
    public void write(List<? extends SFDCSparesDTO> sparesDTOS) throws Exception {
        log.info("Saving data for spares of size:",sparesDTOS.size());
    }
}
