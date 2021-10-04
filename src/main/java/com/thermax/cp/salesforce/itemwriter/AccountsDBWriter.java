package com.thermax.cp.salesforce.itemwriter;

import com.thermax.cp.salesforce.dto.account.SFDCAccountInfoDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
public class AccountsDBWriter implements ItemWriter<SFDCAccountInfoDTO> {



    @Override
    public void write(List<? extends SFDCAccountInfoDTO> accounts) throws Exception {
        log.info("Saving data for accounts of size:",accounts.size());
    }
}
