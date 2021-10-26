package com.thermax.cp.salesforce.dto.contacts;

import lombok.Data;

@Data
public class ContactsDTO {
    private String id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String accountId;
    private String accountName;
    private String email;
    private String salutation;
    private String department;
    private String designation;
    private String phone;
    private String mobilePhone;
    private String mailingStreet;
    private String mailingCity;
    private String mailingState;
    private String mailingPostalCode;
    private String mailingCountry;
    private String icc;
    private String isActive;
    private String isActiveForCp;
}
