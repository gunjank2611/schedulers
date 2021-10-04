package com.thermax.cp.salesforce.dto.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import lombok.Data;

@Data
public class SFDCOrderItemsDTO {
    private AttributesDTO attributes;
    @JsonProperty("Id")
    private String id;
    @JsonProperty("Product2Id")
    private String product2Id;
    @JsonProperty("TH_Product_Code__c")
    private String tH_Product_Code__c;
    @JsonProperty("ListPrice")
    private String listPrice;
    @JsonProperty("UnitPrice")
    private String unitPrice;
    @JsonProperty("TotalPrice")
    private String totalPrice;
    @JsonProperty("THCMG_ERP_User_Id__c")
    private String tHCMG_ERP_User_Id__c;
    @JsonProperty("TH_Asset__c")
    private String tH_Asset__c;
}
