package com.thermax.cp.salesforce.dto.pricebook;

import lombok.Data;

import java.util.List;

@Data
public class SFDCPricebookEntryListDTO {
    private String totalSize;
    private String done;
    private List<SFDCPricebookEntryDTO> records;
}
