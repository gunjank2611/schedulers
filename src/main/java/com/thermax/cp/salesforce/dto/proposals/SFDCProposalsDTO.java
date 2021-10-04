package com.thermax.cp.salesforce.dto.proposals;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import lombok.Data;

@Data
public class SFDCProposalsDTO {

    private AttributesDTO attributes;
    @JsonProperty("Account__c")
    private String account__c;
    @JsonProperty("Additional_Input_By_Proposer__c")
    private String additional_Input_By_Proposer__c;
    @JsonProperty("Additional_Input_By_Requester__c")
    private String additional_Input_By_Requester__c;
    @JsonProperty("Asset__c")
    private String asset__c;
    @JsonProperty("Committed_Date__c")
    private String committed_Date__c;
    @JsonProperty("CreatedDate")
    private String createdDate;
    @JsonProperty("Department__c")
    private Object department__c;
    @JsonProperty("Id")
    private String id;
    @JsonProperty("IsLatestVersion__c")
    private boolean isLatestVersion__c;
    @JsonProperty("IsRevisedAfterClosure__c")
    private boolean isRevisedAfterClosure__c;
    @JsonProperty("LastModifiedDate")
    private String lastModifiedDate;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Opportunity__c")
    private String opportunity__c;
    @JsonProperty("Original_Proposal__c")
    private String original_Proposal__c;
    @JsonProperty("Proposal_Number__c")
    private Object proposal_Number__c;
    @JsonProperty("Proposer_User__c")
    private String proposer_User__c;
    @JsonProperty("Reason__c")
    private Object reason__c;
    @JsonProperty("Status__c")
    private Object status__c;
}
