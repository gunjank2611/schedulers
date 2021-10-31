package com.thermax.cp.salesforce.dto.complaint;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import com.thermax.cp.salesforce.dto.commons.OwnerDTO;
import lombok.Data;

@Data
public class SFDCComplaintsDTO {
    private AttributesDTO attributes;
    @JsonProperty("Id")
    private String id;
    @JsonProperty("CaseNumber")
    private String caseNumber;
    @JsonProperty("Reason")
    private String reason;
    @JsonProperty("OwnerId")
    private String ownerId;
    @JsonProperty("Owner")
    private OwnerDTO owner;
    private String ownerName;
    private String ownerEmail;
    @JsonProperty("Owner_Role_name__c")
    private String ownerRoleName;
    @JsonProperty("Owners_MobilePhone__c")
    private String ownerMobilePhone;
    @JsonProperty("Status")
    private String status;
    @JsonProperty("Origin")
    private String origin;
    @JsonProperty("Subject")
    private String subject;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("THS_Source__c")
    private String tHS_Source__c;
    @JsonProperty("AccountId")
    private String accountId;
    @JsonProperty("AssetId")
    private String assetId;
    @JsonProperty("ContactId")
    private String contactId;
    @JsonProperty("THS_Division__c")
    private String tHS_Division__c;
    @JsonProperty("THS_Case_Source__c")
    private String tHS_Case_Source__c;
    @JsonProperty("CreatedDate")
    private String createdDate;
    @JsonProperty("LastModifiedDate")
    private String lastModifiedDate;

}
