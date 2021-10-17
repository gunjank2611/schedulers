package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.contacts.SFDCContactsDTO;
import com.thermax.cp.salesforce.dto.services.SFDCServiceLogDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Log4j2
public class ServiceLogWriter implements ItemWriter<SFDCServiceLogDTO> {

    @Override
    public void write(List<? extends SFDCServiceLogDTO> sfdcServiceLogDTOS) throws Exception {
        log.info("Saving data for service logs of size: {} ", sfdcServiceLogDTOS.size());
    }
}
