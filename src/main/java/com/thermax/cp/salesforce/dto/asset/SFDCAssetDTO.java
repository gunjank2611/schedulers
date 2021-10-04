package com.thermax.cp.salesforce.dto.asset;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import lombok.Data;

@Data
public class SFDCAssetDTO {
    private AttributesDTO attributes;
    @JsonProperty("Id")
    private String id;
    @JsonProperty("Name")
    private String Name;
    @JsonProperty("InstallDate")
    private String InstallDate;
    @JsonProperty("THSC_Warranty_Expiry_Date__c")
    private String warrantyExpiryDate;
    @JsonProperty("THS_Asset_Service_By_From_ERP__c")
    private String assetServiceByFromERP;
    @JsonProperty("OwnerId")
    private String ownerId;
    @JsonProperty("Owner")
    private AssetOwnerDTO assetOwnerDTO;
    @JsonProperty("AccountId")
    private String accountId;
    @JsonProperty("TH_IBG_Division__c")
    private String ibgDivision;
    @JsonProperty("THS_Division_Type__c")
    private String divisionType;
    @JsonProperty("THCH_Region__c")
    private String region;
    @JsonProperty("THCH_Sales_Order_Number__c")
    private String salesOrderNumber;
    @JsonProperty("Asset_Make__c")
    private String assetMaker;
    @JsonProperty("THCH_Asset_Status__c")
    private String assetStatus;
    @JsonProperty("Calorie_Potential__c")
    private String caloriePotential;
    @JsonProperty("THS_I_C_Scope__c")
    private String icScope;
    @JsonProperty("THSC_Number_of_days_included_in_PO__c")
    private String numberOfDaysIncludedInPO;
    @JsonProperty("THS_Service_Sales_Engineer__c")
    private String serviceSalesEngineer;
    @JsonProperty("TPF_User__c")
    private String user;
    @JsonProperty("THS_WARRANTY_DUR_FR_COMM_DT__c")
    private String warrantyDurationForCommDate;
    @JsonProperty("THS_WARRANTY_DUR_FR_DISP_DT__c")
    private String warrantyDurationForDispDate;
    @JsonProperty("THSC_First_Date_of_Dispatch__c")
    private String firstDateOfDispatch;
    @JsonProperty("TH_Shipment_received_date__c")
    private String shipmentReceivedDate;
    @JsonProperty("TH_IBG_Commissioning_Date__c")
    private String ibgCommissioningDate;
    @JsonProperty("Revised_warranty_expiry_date__c")
    private String revisedWarrantyExpiryDate;
    @JsonProperty("Warranty_Revision_Status__c")
    private String warrantyRevisionStatus;
    @JsonProperty("Reason_for_extended_warranty__c")
    private String reasonForExtendedWarranty;
    @JsonProperty("THSC_Country__c")
    private String country;
    @JsonProperty("THS_Order__r")
    private SFDCOrderDTO orderDTO;
    @JsonProperty("TH_When_to_Engage_Customer_days__c")
    private String whenToEngageCustomerDays;
    @JsonProperty("CreatedDate")
    private String createdDate;
    @JsonProperty("LastModifiedDate")
    private String lastModifiedDate;



}
