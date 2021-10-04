package com.thermax.cp.salesforce.dto.complaint;

import lombok.Data;

import java.util.List;

@Data
public class SFDCComplaintListDTO {
    private String totalSize;
    private String done;
    private List<SFDCComplaintsDTO> records;
}
