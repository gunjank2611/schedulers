package com.thermax.cp.salesforce.dto.opportunity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RecordType {
    @JsonProperty("Name")
    private String name;
}
