package com.thermax.cp.salesforce.dto.contacts;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
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
        @JsonProperty("Department")
        private Object department;
        @JsonProperty("Title")
        private String title;
        @JsonProperty("Phone")
        private String phone;
        @JsonProperty("MobilePhone")
        private String mobilePhone;
        @JsonProperty("MailingStreet")
        private Object mailingStreet;
        @JsonProperty("MailingCity")
        private Object mailingCity;
        @JsonProperty("MailingState")
        private Object mailingState;
        @JsonProperty("MailingPostalCode")
        private Object mailingPostalCode;
        @JsonProperty("MailingCountry")
        private Object mailingCountry;
        @JsonProperty("TH_IBG_International_Calling_Code__c")
        private String tH_IBG_International_Calling_Code__c;
        @JsonProperty("TH_IsActive__c")
        private boolean tH_IsActive__c;

}
