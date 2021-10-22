package com.thermax.cp.salesforce.dto.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import lombok.Data;

import java.util.Date;

@Data
public class SFDCServiceLogDTO {
    public AttributesDTO attributes;
    @JsonProperty("Branded_services_sales__c")
    private String brandedServiceSales;
    @JsonProperty("Comments__c")
    private String comments;
    @JsonProperty("CreatedDate")
    private Date createdDate;
    @JsonProperty("Id")
    private String id;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Visit_Date__c")
    private String visitDate;
}
