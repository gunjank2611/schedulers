package com.thermax.cp.salesforce.dto.commons;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserRoleDTO {
    private AttributesDTO attributes;

    @JsonProperty("Name")
    private String userRoleName;
}
