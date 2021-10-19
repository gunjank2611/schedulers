package com.thermax.cp.salesforce.dto.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import lombok.Data;

import java.util.Date;

@Data
public class SFDCServiceLogDTO {
    public AttributesDTO attributes;
    @JsonProperty("Branded_services_sales__c")
    public String branded_services_sales__c;
    @JsonProperty("Comments__c")
    public String comments__c;
    @JsonProperty("CreatedDate")
    public Date createdDate;
    @JsonProperty("Id")
    public String id;
    @JsonProperty("Name")
    public String name;
    @JsonProperty("Visit_Date__c")
    public String visit_Date__c;
}
