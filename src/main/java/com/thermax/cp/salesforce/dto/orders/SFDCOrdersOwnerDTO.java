package com.thermax.cp.salesforce.dto.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import lombok.Data;


@Data
public class SFDCOrdersOwnerDTO {

    private AttributesDTO attributes;
    @JsonProperty("Name")
    private String name;
}
