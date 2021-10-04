package com.thermax.cp.salesforce.dto.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import lombok.Data;

@Data
public class SFDCOrdersDTO {

    private AttributesDTO attributes;
    @JsonProperty("Id")
    private String id;
    @JsonProperty("OrderNumber")
    private String orderNumber;
    @JsonProperty("THCMG_Customer_PO__c")
    private String tHCMG_Customer_PO__c;
    @JsonProperty("AccountId")
    private String accountId;
    @JsonProperty("THCMG_Payment_Term__c")
    private String tHCMG_Payment_Term__c;
    @JsonProperty("THCMG_ERP_Operating_Unit__c")
    private String tHCMG_ERP_Operating_Unit__c;
    @JsonProperty("THCMG_Cheque_Number__c")
    private String tHCMG_Cheque_Number__c;
    @JsonProperty("THCMG_Transaction_Type_Id__c")
    private String tHCMG_Transaction_Type_Id__c;
    @JsonProperty("THCMG_Bill_To_Location__c")
    private String tHCMG_Bill_To_Location__c;
    @JsonProperty("THCMG_Warehouse__c")
    private String tHCMG_Warehouse__c;
    @JsonProperty("THCMG_Ship_To_Location__c")
    private String tHCMG_Ship_To_Location__c;
    @JsonProperty("THCMG_Date_Ordered__c")
    private String tHCMG_Date_Ordered__c;
    @JsonProperty("THCMG_Payment_Type__c")
    private String tHCMG_Payment_Type__c;
    @JsonProperty("THCMG_Request_Date__c")
    private String tHCMG_Request_Date__c;
    @JsonProperty("EffectiveDate")
    private String effectiveDate;
    @JsonProperty("TotalAmount")
    private double totalAmount;
    @JsonProperty("THCMG_FOB__c")
    private String tHCMG_FOB__c;
    @JsonProperty("TH_Division__c")
    private String tH_Division__c;
    @JsonProperty("THCMG_Freight_Terms__c")
    private String tHCMG_Freight_Terms__c;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("OpportunityId")
    private String opportunityId;
    @JsonProperty("ERP_Order_Number__c")
    private String eRP_Order_Number__c;
    @JsonProperty("TH_Opportunity_Number__c")
    private String tH_Opportunity_Number__c;
    @JsonProperty("Asset__c")
    private String asset__c;
    @JsonProperty("ERP_Credit_Rating__c")
    private Object eRP_Credit_Rating__c;
    @JsonProperty("ERP_invoice__c")
    private Object eRP_invoice__c;
    @JsonProperty("ERP_Invoiced_value__c")
    private Object eRP_Invoiced_value__c;
    @JsonProperty("ERP_order_booked_date__c")
    private Object eRP_order_booked_date__c;
    @JsonProperty("ERP_Order_Value__c")
    private Object eRP_Order_Value__c;
    @JsonProperty("ERP_Order_status__c")
    private Object eRP_Order_status__c;
    @JsonProperty("OwnerId")
    private String ownerId;
    @JsonProperty("Owner")
    private SFDCOrdersOwnerDTO owner;
}
