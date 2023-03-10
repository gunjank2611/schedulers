package com.thermax.cp.salesforce.dto.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thermax.cp.salesforce.dto.commons.AttributesDTO;
import lombok.Data;

@Data
public class SFDCUsersDTO {
    private class Root{
        private AttributesDTO attributes;
        @JsonProperty("Id")
        private String id;
        @JsonProperty("Name")
        private String name;
        @JsonProperty("UserRole")
        private SFDCUserRoleDTO userRole;
        @JsonProperty("Email")
        private String email;
        @JsonProperty("Title")
        private Object title;
        @JsonProperty("Address")
        private Object address;
        @JsonProperty("CurrencyIsoCode")
        private String currencyIsoCode;
        @JsonProperty("ManagerId")
        private String managerId;
        @JsonProperty("Manager")
        private SFDCManagerDTO manager;
        @JsonProperty("THCMG_ERP_USER_ID__c")
        private String tHCMG_ERP_USER_ID__c;
        @JsonProperty("EmployeeNumber")
        private Object employeeNumber;
        @JsonProperty("THCH_Services__c")
        private String tHCH_Services__c;
        @JsonProperty("TH_IBG_Regions__c")
        private String tH_IBG_Regions__c;
        @JsonProperty("THCS_Division__c")
        private String tHCS_Division__c;
    }
}
