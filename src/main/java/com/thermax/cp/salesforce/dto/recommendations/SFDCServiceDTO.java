package com.thermax.cp.salesforce.dto.recommendations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SFDCServiceDTO {
    private AttributesDTO attributes;

    @JsonProperty("THSC_Description__c")
    private  String serviceDescription;

    @JsonProperty("THSC_Contract_Start_Date__c")
    private String contractStartDate;

    @JsonProperty("THSC_Contract_End_Date__c")
    private String contractEndDate;

    @JsonProperty("THSC_No_of_Visits__c")
    private String noOfVisits;

    @JsonProperty("THSC_Ordered_Item__c")
    private String orderedItem;

}
