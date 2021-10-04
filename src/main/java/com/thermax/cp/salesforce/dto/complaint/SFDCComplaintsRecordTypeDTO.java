package com.thermax.cp.salesforce.dto.complaint;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import lombok.Data;

@Data
public class SFDCComplaintsRecordTypeDTO {
    public AttributesDTO attributes;
    @JsonProperty("Name")
    public String name;
}
