package com.thermax.cp.salesforce.dto.services;


import lombok.Data;

import java.util.List;

@Data
public class SFDCServicesListDTO {
    private String totalSize;
    private String done;
    private String nextRecordsUrl;
    private List<SFDCServicesDTO> records;
}
