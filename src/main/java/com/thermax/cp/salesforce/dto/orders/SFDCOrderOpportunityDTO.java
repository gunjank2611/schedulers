package com.thermax.cp.salesforce.dto.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.opportunity.Attributes;
import lombok.Data;


@Data
public class SFDCOrderOpportunityDTO {
    private Attributes attributes;
    @JsonProperty("OwnerId")
    private String ownerId;
    @JsonProperty("TH_Opportunity_Type__c")
    private String opportunityType;
    @JsonProperty("Owner")
    private SFDCOrderOwner owner;
}



