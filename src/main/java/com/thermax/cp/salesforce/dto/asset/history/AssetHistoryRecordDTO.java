package com.thermax.cp.salesforce.dto.asset.history;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AssetHistoryRecordDTO {

    private AssetHistoryAttributesDTO attributes;

    @JsonProperty("Id")
    private String id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("TH_Asset__c")
    private String tH_Asset__c;

    @JsonProperty("TH_Spare__c")
    private String tH_Spare__c;

    @JsonProperty("TH_Asset_Name__c")
    private String tH_Asset_Name__c;

    @JsonProperty("TH_Change_Type__c")
    private String tH_Change_Type__c;

    @JsonProperty("TH_Description_for__c")
    private String tH_Description_for__c;

}
