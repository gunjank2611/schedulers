package com.thermax.cp.salesforce.dto.contacts;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOrdersOwnerDTO;
import lombok.Data;

@Data
public class SFDCContactsDTO {

    private AttributesDTO attributes;
    @JsonProperty("Id")
    private String id;
    @JsonProperty("FirstName")
    private String firstName;
    @JsonProperty("MiddleName")
    private String middleName;
    @JsonProperty("LastName")
    private String lastName;
    @JsonProperty("AccountId")
    private String accountId;
    @JsonProperty("Account")
    private SFDCAccountDTO account;
    @JsonProperty("Email")
    private String email;
    @JsonProperty("Salutation")
    private String salutation;
    @JsonProperty("Department")
    private String department;
    @JsonProperty("Title")
    private String title;
    @JsonProperty("Phone")
    private String phone;
    @JsonProperty("MobilePhone")
    private String mobilePhone;
    @JsonProperty("MailingStreet")
    private String mailingStreet;
    @JsonProperty("MailingCity")
    private String mailingCity;
    @JsonProperty("MailingState")
    private String mailingState;
    @JsonProperty("MailingPostalCode")
    private String mailingPostalCode;
    @JsonProperty("MailingCountry")
    private String mailingCountry;
    @JsonProperty("TH_IBG_International_Calling_Code__c")
    private String tH_IBG_International_Calling_Code__c;
    @JsonProperty("TH_IsActive__c")
    private boolean tH_IsActive__c;
    @JsonProperty("TMAX_isActiveForCP__c")
    private boolean tMAX_isActiveForCP__c;

}
