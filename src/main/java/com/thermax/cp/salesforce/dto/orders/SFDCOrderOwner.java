package com.thermax.cp.salesforce.dto.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.opportunity.Attributes;
import lombok.Data;

@Data
public class SFDCOrderOwner {
    private Attributes attributes;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Email")
    private String email;
    @JsonProperty("MobilePhone")
    private String mobilePhone;
    @JsonProperty("UserRole")
    private SFDCUserRole userRole;
}
