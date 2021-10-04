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
    @JsonProperty("RecordType")
    private SFDCComplaintsRecordTypeDTO recordType;
    @JsonProperty("Reason")
    private Object reason;
    @JsonProperty("OwnerId")
    private String ownerId;
    @JsonProperty("Owner")
    private SFDCComplaintsOwnerDTO owner;
    @JsonProperty("Status")
    private String status;
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
    @JsonProperty("Zone__c")
    private Object zone__c;
    @JsonProperty("THS_Sub_Division__c")
    private Object tHS_Sub_Division__c;
    @JsonProperty("Origin")
    private Object origin;
    @JsonProperty("THS_Case_Source__c")
    private Object tHS_Case_Source__c;
    @JsonProperty("TH_Region__c")
    private Object tH_Region__c;
    @JsonProperty("THS_Dept_Dependency__c")
    private Object tHS_Dept_Dependency__c;
    @JsonProperty("THC_Country__c")
    private Object tHC_Country__c;
    @JsonProperty("THS_Asset_Status__c")
    private Object tHS_Asset_Status__c;
    @JsonProperty("Priority")
    private String priority;
    @JsonProperty("CreatedDate")
    private String createdDate;
    @JsonProperty("LastModifiedDate")
    private String lastModifiedDate;

}
