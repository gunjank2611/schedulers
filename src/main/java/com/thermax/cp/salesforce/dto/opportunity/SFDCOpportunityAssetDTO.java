package com.thermax.cp.salesforce.dto.opportunity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SFDCOpportunityAssetDTO
{
    private AttributesDTO attributes;
    @JsonProperty("Id")
    private String id;
    @JsonProperty("Asset__c")
    private String assetId;

}