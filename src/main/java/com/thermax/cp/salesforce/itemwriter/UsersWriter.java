package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.spares.SFDCSparesDTO;
import com.thermax.cp.salesforce.dto.users.SFDCUsersDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Log4j2
public class UsersWriter implements ItemWriter<SFDCUsersDTO> {
    @Override
    public void write(List<? extends SFDCUsersDTO> sfdcUsersDTOS) throws Exception {
        log.info("Saving data for spares of size: {} ",sfdcUsersDTOS.size());
    }
}
