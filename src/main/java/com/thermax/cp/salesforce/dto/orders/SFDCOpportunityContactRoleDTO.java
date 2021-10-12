package com.thermax.cp.salesforce.dto.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import lombok.Data;

@Data
public class SFDCOpportunityContactRoleDTO {
    private AttributesDTO attributes;
    @JsonProperty("Id")
    private String id;
    @JsonProperty("ContactId")
    private String contactId;
    @JsonProperty("IsPrimary")
    private String isPrimary;
    @JsonProperty("OpportunityId")
    private String opportunityId;
    @JsonProperty("Role")
    private String contactRole;
}
