package com.thermax.cp.salesforce.dto.opportunity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
public class SFDCOpportunityDTO{
    private Attributes attributes;
    @JsonProperty("Id")
    private String id;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("AccountId")
    private String accountId;
    @JsonProperty("TH_Opportunity_Number__c")
    private String opportunityNumber;
    @JsonProperty("THSC_Opportunity_Asset_Id__c")
    private String opportunityAssetId;
    @JsonProperty("CloseDate")
    private String closeDate;
    @JsonProperty("StageName")
    private String stageName;
    @JsonProperty("Amount")
    private double amount;
    @JsonProperty("TH_Opportunity_Type__c")
    private String tH_Opportunity_Type__c;
    @JsonProperty("TH_Customer_Type__c")
    private String tH_Customer_Type__c;
    @JsonProperty("TH_Region__c")
    private String tH_Region__c;
    @JsonProperty("THCH_Zone__c")
    private String tHCH_Zone__c;
    @JsonProperty("THCH_Territory__c")
    private Object tHCH_Territory__c;
    @JsonProperty("Probability")
    private double probability;
    @JsonProperty("TH_Product_Family__c")
    private String tH_Product_Family__c;
    @JsonProperty("THCH_Techno_Commercial_Acceptance_Date__c")
    private String tHCH_Techno_Commercial_Acceptance_Date__c;
    @JsonProperty("TH_RFQ_Completed__c")
    private String tH_RFQ_Completed__c;
    @JsonProperty("TH_Reason_for_Closed_Lost_Won_Drop__c")
    private String tH_Reason_for_Closed_Lost_Won_Drop__c;
    @JsonProperty("CMG_Won_against_Whom__c")
    private String cMG_Won_against_Whom__c;
    @JsonProperty("TH_Lost_to_Whom__c")
    private Object tH_Lost_to_Whom__c;
    @JsonProperty("TH_Closing_Summary__c")
    private String tH_Closing_Summary__c;
    @JsonProperty("THCMG_Date_Ordered__c")
    private String tHCMG_Date_Ordered__c;
    @JsonProperty("THCMG_Bill_To_Location__c")
    private String tHCMG_Bill_To_Location__c;
    @JsonProperty("THCMG_Ship_To_Location__c")
    private String tHCMG_Ship_To_Location__c;
    @JsonProperty("THCMG_Cheque_Number__c")
    private String tHCMG_Cheque_Number__c;
    @JsonProperty("THCMG_Customer_PO__c")
    private String tHCMG_Customer_PO__c;
    @JsonProperty("THCMG_ERP_Division__c")
    private String tHCMG_ERP_Division__c;
    @JsonProperty("THCMG_Payment_Term__c")
    private String tHCMG_Payment_Term__c;
    @JsonProperty("THCMG_Warehouse__c")
    private String tHCMG_Warehouse__c;
    @JsonProperty("THCMG_Transaction_Type__c")
    private String tHCMG_Transaction_Type__c;
    @JsonProperty("THCMG_FOB__c")
    private String tHCMG_FOB__c;
    @JsonProperty("THCMG_Freight_Terms__c")
    private String tHCMG_Freight_Terms__c;
    @JsonProperty("OwnerId")
    private String ownerId;
}

