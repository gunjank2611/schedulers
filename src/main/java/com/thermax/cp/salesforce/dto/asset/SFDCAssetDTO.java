package com.thermax.cp.salesforce.dto.asset;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import com.thermax.cp.salesforce.dto.commons.OwnerDTO;
import lombok.Data;

import java.util.Date;

@Data
public class SFDCAssetDTO{
    private AttributesDTO attributes;
    @JsonProperty("Id")
    private String id;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("InstallDate")
    private Object installDate;
    @JsonProperty("THSC_Warranty_Expiry_Date__c")
    private Object tHSC_Warranty_Expiry_Date__c;
    @JsonProperty("THS_Asset_Service_By_From_ERP__c")
    private Object tHS_Asset_Service_By_From_ERP__c;
    @JsonProperty("OwnerId")
    private String ownerId;
    @JsonProperty("Owner")
    private OwnerDTO assetOwnerDTO;
    private String ownerName;
    private String ownerMobile;
    private String ownerUserRoleName;
    private String ownerEmail;
    @JsonProperty("AccountId")
    private String accountId;
    @JsonProperty("TH_IBG_Division__c")
    private String tH_IBG_Division__c;
    @JsonProperty("THS_Division_Type__c")
    private Object tHS_Division_Type__c;
    @JsonProperty("THCH_Region__c")
    private String tHCH_Region__c;
    @JsonProperty("THCH_Sales_Order_Number__c")
    private String tHCH_Sales_Order_Number__c;
    @JsonProperty("Asset_Make__c")
    private String asset_Make__c;
    @JsonProperty("THCH_Asset_Status__c")
    private String tHCH_Asset_Status__c;
    @JsonProperty("Calorie_Potential__c")
    private Object calorie_Potential__c;
    @JsonProperty("THSC_I_C_Scope__c")
    private Object tHSC_I_C_Scope__c;
    @JsonProperty("THSC_Number_of_days_included_in_PO__c")
    private Object tHSC_Number_of_days_included_in_PO__c;
    @JsonProperty("THS_I_C_Scope__c")
    private Object tHS_I_C_Scope__c;
    @JsonProperty("THS_Service_Sales_Engineer__c")
    private Object tHS_Service_Sales_Engineer__c;
    @JsonProperty("TPF_User__c")
    private Object tPF_User__c;
    @JsonProperty("THS_WARRANTY_DUR_FR_COMM_DT__c")
    private Object tHS_WARRANTY_DUR_FR_COMM_DT__c;
    @JsonProperty("THS_WARRANTY_DUR_FR_DISP_DT__c")
    private Object tHS_WARRANTY_DUR_FR_DISP_DT__c;
    @JsonProperty("THSC_First_Date_of_Dispatch__c")
    private Object tHSC_First_Date_of_Dispatch__c;
    @JsonProperty("TH_Shipment_received_date__c")
    private Object tH_Shipment_received_date__c;
    @JsonProperty("TH_IBG_Commissioning_Date__c")
    private Object tH_IBG_Commissioning_Date__c;
    @JsonProperty("Revised_warranty_expiry_date__c")
    private Object revised_warranty_expiry_date__c;
    @JsonProperty("Warranty_Revision_Status__c")
    private Object warranty_Revision_Status__c;
    @JsonProperty("Reason_for_extended_warranty__c")
    private String reasonForExtendedWarranty;
    @JsonProperty("THSC_Country__c")
    private Object tHSC_Country__c;
    @JsonProperty("THS_Order__r")
    private Object tHS_Order__r;
    @JsonProperty("TH_When_to_Engage_Customer_days__c")
    private Object tH_When_to_Engage_Customer_days__c;
    @JsonProperty("CreatedDate")
    private Date createdDate;
    @JsonProperty("LastModifiedDate")
    private Date lastModifiedDate;
    @JsonProperty("TMAX_TCA_User__c")
    private String tMAX_TCA_User__c;
    @JsonProperty("TMAX_TCA_User__r")
    private TMAXTCAUserRDTO tMAX_TCA_User__r;
    private String caUsername;
    private String caUserMobile;
    private String caUserRoleName;
    private String caUserEmail;
    @JsonProperty("TMAX_Service_SPOC_CP__c")
    private String tMAX_Service_SPOC_CP__c;
    @JsonProperty("TMAX_Service_SPOC_CP__r")
    private TMAXServiceSPOCCPRDTO tMAX_Service_SPOC_CP__r;
    private String serviceSpocName;
    private String serviceSpocMobile;
    private String serviceSpocUserRoleName;
    private String serviceSpocEmail;
    @JsonProperty("TMAX_Spares_Sales_SPOC_CP__c")
    private String tMAX_Spares_Sales_SPOC_CP__c;
    @JsonProperty("TMAX_Spares_Sales_SPOC_CP__r")
    private TMAXSparesSalesSPOCCPRDTO tMAX_Spares_Sales_SPOC_CP__r;
    private String spareSalesSpocName;
    private String spareSalesSpocMobile;
    private String spareSalesSpocUserRoleName;
    private String spareSalesSpocEmail;
    @JsonProperty("TMAX_Service_Sales_SPOC_CP__c")
    private String tMAX_Service_Sales_SPOC_CP__c;
    @JsonProperty("TMAX_Service_Sales_SPOC_CP__r")
    private TMAXServiceSalesSPOCCPRDTO tMAX_Service_Sales_SPOC_CP__r;
    private String servicesSalesSpocName;
    private String serviceSalesSpocUserRoleName;
    private String serviceSalesSpocMobile;
    private String serviceSalesSpocEmail;
    @JsonProperty("ContactId")
    private Object contactId;
    @JsonProperty("Contact")
    private Object contact;
    @JsonProperty("TMAX_Product_Family_CP__c")
    private String tMAX_Product_Family_CP__c;
    @JsonProperty("Customer_Address__c")
    private String customer_Address__c;
    @JsonProperty("THSC_State__c")
    private String tHSC_State__c;

}
