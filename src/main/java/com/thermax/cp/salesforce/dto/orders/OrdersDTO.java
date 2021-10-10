package com.thermax.cp.salesforce.dto.orders;

import lombok.Data;

@Data
public class OrdersDTO {

    private String id;
    private String orderNumber;
    private String tHCMG_Customer_PO__c;
    private String accountId;
    private String tHCMG_Payment_Term__c;
    private String tHCMG_ERP_Operating_Unit__c;
    private String tHCMG_Cheque_Number__c;
    private String tHCMG_Transaction_Type_Id__c;
    private String tHCMG_Bill_To_Location__c;
    private String tHCMG_Warehouse__c;
    private String tHCMG_Ship_To_Location__c;
    private String tHCMG_Date_Ordered__c;
    private String tHCMG_Payment_Type__c;
    private String tHCMG_Request_Date__c; //
    private String effectiveDate;
    private String totalAmount;
    private String tHCMG_FOB__c;
    private String tH_Division__c;
    private String tHCMG_Freight_Terms__c;
    private String status;
    private String opportunityId;
    private String eRP_Order_Number__c;
    private String tH_Opportunity_Number__c;
    private String asset__c;
    private String ownerId;
    private String ownerName;
    private String ownerEmail;
    private String ownerContact;
    private String ownerRole;
    private String opportunityType;
}
