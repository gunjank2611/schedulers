package com.thermax.cp.salesforce.itemprocessor;

import com.thermax.cp.salesforce.dto.account.SFDCAccountInfoDTO;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class AccountsProcessor implements ItemProcessor<SFDCAccountInfoDTO,SFDCAccountInfoDTO> {
    @Override
    public SFDCAccountInfoDTO process(SFDCAccountInfoDTO sfdcAccountInfoDTOs) throws Exception {

        return sfdcAccountInfoDTOs;
    }
}
