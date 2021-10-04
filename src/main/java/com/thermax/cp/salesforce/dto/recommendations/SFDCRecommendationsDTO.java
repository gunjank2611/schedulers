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
        @JsonProperty("CreatedDate")
        private  String creationDate;
        @JsonProperty("LastModifiedDate")
        private String lastModifiedDate;
        @JsonProperty("Name")
        private String name;
        @JsonProperty("OwnerId")
        private String ownerId;
        @JsonProperty("Owner")
        private OwnerDTO ownerDTO;
        @JsonProperty("TH_Description_for__c")
        private String description;
        @JsonProperty("TH_Interaction_Planned_Date__c")
        private String interactionPlannedDate;
        @JsonProperty("TH_Opportunity__c")
        private String opportunity;
        @JsonProperty("TH_Planned_Activities__c")
        private String plannedActivities;
        @JsonProperty("TH_Planned_Shutdown_date__c")
        private String plannedShutDownDate;
        @JsonProperty("TH_Potential_Value__c")
        private String potentialValue;
        @JsonProperty("TH_Purpose__c")
        private String purpose;
        @JsonProperty("TH_Recommendation_Close_Date__c")
        private String recommendationCloseDate;
        @JsonProperty("TH_Reject_Reason__c")
        private String rejectReason;
        @JsonProperty("TH_Reminder_For_Next_Contact__c")
        private String reminderForNextContact;
        @JsonProperty("TH_Selected_Services__c")
        private String selectedServices;
        @JsonProperty("TH_Selected_Spares__c")
        private String selectedSpares;
        @JsonProperty("TH_Service__c")
        private String service;
        @JsonProperty("TH_Service__r")
        private SFDCServiceDTO sfdcServiceDTO;
        @JsonProperty("TH_Spare__c")
        private String spare;
        @JsonProperty("TH_Spare__r")
        private SFDCSpareDTO sfdcSpareDTO;
        @JsonProperty("TH_SubType__c")
        private String recommendationSubType;
        @JsonProperty("TH_Type__c")
        private String recommendationType;

}




