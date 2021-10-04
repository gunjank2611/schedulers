package com.thermax.cp.salesforce.dto.opportunity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import lombok.Data;

@Data
public class SFDCOpportunityLineItemsDTO{
    private AttributesDTO attributes;
    @JsonProperty("Id")
    private String id;
    @JsonProperty("OpportunityId")
    private String opportunityId;
    @JsonProperty("Product2Id")
    private String product2Id;
    @JsonProperty("TH_Product_Family__c")
    private String tH_Product_Family__c;
    @JsonProperty("TH_Forecast_Category_Name__c")
    private String tH_Forecast_Category_Name__c;
    @JsonProperty("TH_ENV_Enviro_Quantity__c")
    private double tH_ENV_Enviro_Quantity__c;
    @JsonProperty("ProductCode")
    private Object productCode;
    @JsonProperty("ListPrice")
    private double listPrice;
    @JsonProperty("UnitPrice")
    private double unitPrice;
    @JsonProperty("Quantity")
    private double quantity;
    @JsonProperty("THCMG_Product_Name__c")
    private String tHCMG_Product_Name__c;
    @JsonProperty("TH_CNH_Division__c")
    private String tH_CNH_Division__c;
}
