package com.thermax.cp.salesforce.dto.account;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountRequestDTO {
    private String TH_CIN_Number__c;
    private String TH_GST_Number__c;
}
