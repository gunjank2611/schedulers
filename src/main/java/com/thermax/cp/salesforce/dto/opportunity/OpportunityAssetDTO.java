package com.thermax.cp.salesforce.dto.opportunity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.account.SFDCAccountInfoDTO;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import lombok.Data;

import java.util.List;

@Data
public class OpportunityAssetDTO {
    private String totalSize;
    private String done;
    private List<SFDCOpportunityAssetDTO> records;
}
@Data
class SFDCOpportunityAssetDTO
{
    private AttributesDTO attributes;
    @JsonProperty("Id")
    private String id;
    @JsonProperty("Asset__c")
    private String assetId;

}
