package com.thermax.cp.salesforce.dto.asset;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import lombok.Data;

@Data
public class SFDCOrderDTO {
    private AttributesDTO attributes;

    @JsonProperty("THCMG_Date_Ordered__c")
    private String orderedDate;
}
