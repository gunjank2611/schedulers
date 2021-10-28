package com.thermax.cp.salesforce.dto.proposals;

import com.thermax.cp.salesforce.dto.orders.SFDCOrdersDTO;
import lombok.Data;

import java.util.List;

@Data
public class SFDCProposalsListDTO {
    private String totalSize;
    private String done;
    private String nextRecordsUrl;
    private List<SFDCProposalsDTO> records;
}
