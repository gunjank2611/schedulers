package com.thermax.cp.salesforce.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import lombok.Data;

@Data
public class SFDCProductInfoDTO {

    private AttributesDTO attributes;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Id")
    private String id;

    @JsonProperty("TH_UOM__c")
    private boolean uom;

    @JsonProperty("Description")
    private String description;


    @JsonProperty("IsActive")
    private boolean isActive;

    @JsonProperty("ProductCode")
    private String productCode;


}
