package com.thermax.cp.salesforce.dto.complaint;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import lombok.Data;

@Data
public class SFDCComplaintsDTO {
    private AttributesDTO attributes;
    @JsonProperty("Id")
    private String id;
    @JsonProperty("CaseNumber")
    private String caseNumber;
    @JsonProperty("Reason")
    private Object reason;
    @JsonProperty("OwnerId")
    private String ownerId;
    @JsonProperty("Owner")
    private SFDCComplaintsOwnerDTO owner;
    private String ownerName;
    private String ownerEmail;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("Origin")
    private String origin;
    @JsonProperty("Subject")
    private Object subject;
    @JsonProperty("Description")
    private Object description;
    @JsonProperty("THS_Source__c")
    private Object tHS_Source__c;
    @JsonProperty("AccountId")
    private Object accountId;
    @JsonProperty("AssetId")
    private Object assetId;
    @JsonProperty("ContactId")
    private Object contactId;
    @JsonProperty("THS_Division__c")
    private Object tHS_Division__c;
    @JsonProperty("THS_Case_Source__c")
    private Object tHS_Case_Source__c;
    @JsonProperty("CreatedDate")
    private String createdDate;
    @JsonProperty("LastModifiedDate")
    private String lastModifiedDate;

}
