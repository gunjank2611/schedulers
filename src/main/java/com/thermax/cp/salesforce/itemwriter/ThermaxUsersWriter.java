package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.users.SFDCUsersDTO;
import com.thermax.cp.salesforce.dto.users.ThermaxUsersDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

@Log4j2
public class ThermaxUsersWriter implements ItemWriter<ThermaxUsersDTO> {
@Override
public void write(List<? extends ThermaxUsersDTO> sfdcUsersDTOS) throws Exception {
        log.info("Saving data for thermax users of size: {} ",sfdcUsersDTOS.size());
        }
}

