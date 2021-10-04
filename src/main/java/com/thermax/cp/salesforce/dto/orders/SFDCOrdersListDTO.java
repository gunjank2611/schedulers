package com.thermax.cp.salesforce.dto.orders;

import com.thermax.cp.salesforce.dto.complaint.SFDCComplaintsDTO;
import lombok.Data;

import java.util.List;

@Data
public class SFDCOrdersListDTO {

    private String totalSize;
    private String done;
    private List<SFDCOrdersDTO> records;
}
