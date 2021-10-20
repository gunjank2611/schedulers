package com.thermax.cp.salesforce.dto.pricebook;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import lombok.Data;

@Data
public class SFDCPricebookDTO {

    public AttributesDTO attributes;

    @JsonProperty("Id")
    public String id;
    @JsonProperty("Description")
    public String description;
    @JsonProperty("IsActive")
    public boolean active;
    @JsonProperty("Name")
    public String name;
}