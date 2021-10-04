package com.thermax.cp.salesforce.dto.complaint;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import lombok.Data;

@Data
public class SFDCComplaintsOwnerDTO {
    private AttributesDTO attributes;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Email")
    private String email;
}
