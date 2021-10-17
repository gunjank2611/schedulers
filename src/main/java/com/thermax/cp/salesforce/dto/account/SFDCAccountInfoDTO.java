package com.thermax.cp.salesforce.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import lombok.Data;



@Data
public class SFDCAccountInfoDTO {

    private AttributesDTO attributes;

    @JsonProperty("Id")
    private String accountId;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Type")
    private String type;
    @JsonProperty("Website")
    private String website;
    @JsonProperty("AccountSource")
    private String accountSource;
    @JsonProperty("TH_Existing_Customer_for_Water_Division__c")
    private String customerForWaterDivision;
    @JsonProperty("TH_Existing_Customer_forHeating_Division__c")
    private String customerForHeatingDivision;
    @JsonProperty("THCMG_ERP_Operating_Unit__c")
    private String erpOperatingUnit;
    @JsonProperty("Rating")
    private String rating;
    @JsonProperty("TH_Status__c")
    private String status;
    @JsonProperty("TH_CIN_Number__c")
    private String cinNumber;
    @JsonProperty("TH_GST_Number__c")
    private String gstNumber;
    @JsonProperty("TH_Pan_No__c")
    private String panNumber;
    @JsonProperty("ParentId")
    private String parentId;
    @JsonProperty("Phone")
    private String phone;
    @JsonProperty("Account_Number__c")
    private String accountNumber;
    @JsonProperty("THCH_Email__c")
    private String emailAddress;
    @JsonProperty("TH_IsActive__c")
    private boolean active;

}
