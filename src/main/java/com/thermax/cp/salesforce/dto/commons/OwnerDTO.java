package com.thermax.cp.salesforce.dto.commons;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OwnerDTO {
    private AttributesDTO attributes;

    @JsonProperty("Name")
    private String ownerName;

    @JsonProperty("UserRole")
    private UserRoleDTO userRole;

    @JsonProperty("MobilePhone")
    private String mobilePhone;

    @JsonProperty("Email")
    private String email;
}
