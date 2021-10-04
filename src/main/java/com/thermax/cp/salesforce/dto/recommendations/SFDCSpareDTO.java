package com.thermax.cp.salesforce.dto.recommendations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import lombok.Data;

@Data
public class SFDCSpareDTO {
    private AttributesDTO attributes;

    @JsonProperty("TH_DESCRIPTION__c")
    private String spareDescription;

    @JsonProperty("TH_LifeCycle_days__c")
    private String lifeCycleDays;

    @JsonProperty("TH_ORDER_LINE_ID__c")
    private String orderLineId;
}