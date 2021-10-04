package com.thermax.cp.salesforce.dto.asset;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AssetRecordDTO {

    private AssetRecordAttributesDTO attributes;

    @JsonProperty("Id")
    private String id;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("TH_IBG_Division__c")
    private String tH_IBG_Division__c;

    @JsonProperty("THCH_Region__c")
    private String tHCH_Region__c;

    @JsonProperty("Asset_Make__c")
    private String asset_Make__c;

    @JsonProperty("AccountId")
    private String accountId;

    @JsonProperty("THCH_Asset_Status__c")
    private String tHCH_Asset_Status__c;

    @JsonProperty("THCH_Sales_Order_Number__c")
    private String tHCH_Sales_Order_Number__c;
}
