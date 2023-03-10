package com.thermax.cp.salesforce.dto.account;

import lombok.Data;

import java.util.List;

@Data
public class AccountListDTO {

    private String totalSize;
    private String done;
    private List<SFDCAccountInfoDTO> records;
}
