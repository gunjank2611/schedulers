package com.thermax.cp.salesforce.dto.opportunity;

import lombok.Data;

import java.util.List;

@Data
public class SFDCOpportunityLineItemsListDTO {

    private String totalSize;
    private String done;
    private List<SFDCOpportunityLineItemsDTO> records;
    private String nextRecordsUrl;
}
