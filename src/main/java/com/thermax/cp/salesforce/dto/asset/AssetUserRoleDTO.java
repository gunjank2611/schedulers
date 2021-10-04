package com.thermax.cp.salesforce.dto.asset;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import lombok.Data;

@Data
public class AssetUserRoleDTO  {

    private AttributesDTO attributes;

    @JsonProperty("Name")
    private String userRoleName;

}
