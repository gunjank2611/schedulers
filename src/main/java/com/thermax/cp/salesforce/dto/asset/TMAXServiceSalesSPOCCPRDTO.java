package com.thermax.cp.salesforce.dto.asset;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import lombok.Data;

@Data
public class TMAXServiceSalesSPOCCPRDTO{
    private AttributesDTO attributes;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("UserRole")
    private AssetUserRoleDTO userRole;
    @JsonProperty("MobilePhone")
    private String mobilePhone;
    @JsonProperty("Email")
    private String email;
}
