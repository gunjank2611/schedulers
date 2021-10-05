package com.thermax.cp.salesforce.dto.recommendations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class SFDCRecommendationsDTO {
        private AttributesDTO attributes;
        @JsonProperty("Id")
        private String id;
        @JsonProperty("TH_Asset__c")
        private String asset;
        @JsonProperty("TH_Account_Name__c")
        private String accountName;
        @JsonProperty("TH_Status__c")
        private String status;
        @JsonProperty("CreatedDate")
        private  String createdDate;
        @JsonProperty("LastModifiedDate")
        private String lastModifiedDate;
        @JsonProperty("Name")
        private String name;
        @JsonProperty("OwnerId")
        private String ownerId;
        @JsonProperty("TH_Description_for__c")
        private String plannedShutdownDescription;
        @JsonProperty("TH_Interaction_Planned_Date__c")
        private String interactionPlannedDate;
        @JsonProperty("TH_Opportunity__c")
        private String opportunity;
        @JsonProperty("TH_Planned_Activities__c")
        private String plannedActivities;
        @JsonProperty("TH_Planned_Shutdown_date__c")
        private String plannedShutDownDate;
        @JsonProperty("TH_Recommendation_Close_Date__c")
        private String recommendationCloseDate;
        @JsonProperty("TH_Selected_Services__c")
        private String selectedServices;
        @JsonProperty("TH_Selected_Spares__c")
        private String selectedSpares;
        @JsonProperty("TH_Service__c")
        private String service;
        @JsonProperty("TH_Spare__c")
        private String spare;
        @JsonProperty("TH_SubType__c")
        private String recommendationSubType;
        @JsonProperty("TH_Type__c")
        private String recommendationType;

}




