package com.thermax.cp.salesforce.dto.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import lombok.Data;

@Data
public class SFDCUserRoleDTO{
    private AttributesDTO attributes;
    @JsonProperty("Name")
    private String name;
}
