package com.thermax.cp.salesforce.dto.spares;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import lombok.Data;

@Data
public class SFDCSparesDTO {

    private AttributesDTO attributes;

    @JsonProperty("Id")
    private String id;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Th_Asset__c")
    private String asset;
    @JsonProperty("THSC_Region__c")
    private String region;
    @JsonProperty("THSC_Division__c")
    private String division;
    @JsonProperty("TH_SALES_ORDER_NUMBER__c")
    private String salesOrderNumber;
    @JsonProperty("TH_QTY__c")
    private String quantity;
    @JsonProperty("TH_UNIT_SELLING_PRICE__c")
    private Double unitSellingPrice;
    @JsonProperty("TH_UOM__c")
    private String uom;
    @JsonProperty("TH_ORDERED_ITEM__c")
    private String orderedItem;
    @JsonProperty("TH_LINE_TOTAL__c")
    private String lineTotal;
    @JsonProperty("TH_ORDER_LINE_ID__c")
    private String orderLineId;
    @JsonProperty("TH_LINE_STATUS__c")
    private String lineStatus;
    @JsonProperty("TH_DESCRIPTION__c")
    private String description;
    @JsonProperty("TH_Thermax_Spare__c")
    private boolean thermaxSpare;
    @JsonProperty("TH_LifeCycle_days__c")
    private String lifecycleDays;
    @JsonProperty("TH_Account_Id__c")
    private String account;


}
