package com.thermax.cp.salesforce.dto.opportunity;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter

public class OpportunityAssetDTO {
    private String totalSize;
    private String done;
    private List<SFDCOpportunityAssetDTO> records;
}

