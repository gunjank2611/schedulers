package com.thermax.cp.salesforce.dto.asset;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import lombok.Data;

@Data
public class SFDCAssetHistoryDTO {
        private AttributesDTO attributes;
        @JsonProperty("Id")
        private String id;
        @JsonProperty("Name")
        private String name;
        @JsonProperty("TH_Asset__c")
        private String tH_Asset__c;
        @JsonProperty("TH_Spare__c")
        private Object tH_Spare__c;
        @JsonProperty("TH_Change_Type__c")
        private String tH_Change_Type__c;
        @JsonProperty("TH_Description_for__c")
        private String tH_Description_for__c;
        @JsonProperty("TH_Account_Id__c")
        private String tH_Account_Id__c;
        @JsonProperty("LastModifiedDate")
        private String lastModifiedDate;
}
