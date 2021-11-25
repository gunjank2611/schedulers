package com.thermax.cp.salesforce.dto.asset;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import lombok.Data;

@Data
public class SFDCEligibleSparesServicesDTO {
    public AttributesDTO attributes;

    @JsonProperty("Id")
    public String id;
    @JsonProperty("Asset__c")
    public String asset__c;
    @JsonProperty("CurrencyIsoCode")
    public String currencyIsoCode;
    @JsonProperty("Life_Cycle_Date__c")
    public Object life_Cycle_Date__c;
    @JsonProperty("Name")
    public String name;
    @JsonProperty("Part_Number__c")
    public String part_Number__c;
    @JsonProperty("TH_Thermax_Spare__c")
    public boolean tH_Thermax_Spare__c;
    @JsonProperty("Type__c")
    public String type__c;
    @JsonProperty("When_to_Engage_Customer__c")
    public Object when_to_Engage_Customer__c;
    @JsonProperty("Description__c")
    private String description;
}
