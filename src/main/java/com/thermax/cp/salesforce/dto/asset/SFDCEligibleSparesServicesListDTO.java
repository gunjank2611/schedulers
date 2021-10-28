package com.thermax.cp.salesforce.dto.asset;

import lombok.Data;

import java.util.List;

@Data
public class SFDCEligibleSparesServicesListDTO {
    private String totalSize;
    private String done;
    private List<SFDCEligibleSparesServicesDTO> records;
    private String nextRecordsUrl;
}
