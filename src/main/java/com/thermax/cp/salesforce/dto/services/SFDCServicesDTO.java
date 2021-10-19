package com.thermax.cp.salesforce.dto.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import lombok.Data;

@Data
public class SFDCServicesDTO {

    private AttributesDTO attributes;

    @JsonProperty("Id")
    private String id;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("TH_Count_of_Visit_Log__c")
    private String countOfVisitLog;
    @JsonProperty("THSC_No_of_Visits__c")
    private String noOfVisits;
    @JsonProperty("THSC_Contract_Start_Date__c")
    private String contractStartDate;
    @JsonProperty("THSC_Contract_End_Date__c")
    private String contractEndDate;
    @JsonProperty("THSC_Asset__c")
    private String assetId;
    @JsonProperty("THSC_Region__c")
    private String region;
    @JsonProperty("THSC_Division__c")
    private String division;
    @JsonProperty("THSC_Executor__c")
    private String executor;
    @JsonProperty("THSC_Sales_Order_Number__c")
    private String salesOrderNumber;
    @JsonProperty("THSC_Quantity__c")
    private String quantity;
    @JsonProperty("THSC_Unit_Selling_Price__c")
    private String unitSellingPrice;
    @JsonProperty("THSC_UOM__c")
    private String uom;
    @JsonProperty("THSC_Ordered_Item__c")
    private String orderedItem;
    @JsonProperty("THSC_Line_Total__c")
    private String lineTotal;
    @JsonProperty("THSC_Order_Line_ID__c")
    private String orderLineId;
    @JsonProperty("TH_Line_Status__c")
    private String lineStatus;
    @JsonProperty("THSC_Description__c")
    private String description;




}
