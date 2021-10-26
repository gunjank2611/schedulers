package com.thermax.cp.salesforce.mapper;

import com.thermax.cp.salesforce.dto.contacts.ContactsDTO;
import com.thermax.cp.salesforce.dto.contacts.SFDCContactsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ContactsMapper {
    @Mapping(source = "account.name", target = "accountName")
    @Mapping(source = "title", target = "designation")
    @Mapping(source = "TH_IBG_International_Calling_Code__c", target = "icc")
    @Mapping(source = "TH_IsActive__c", target = "isActive")
    @Mapping(source = "TMAX_isActiveForCP__c", target = "isActiveForCp")
    ContactsDTO convertToContactsFromSFDCContacts(final SFDCContactsDTO sfdcContactsDTO);

    List<ContactsDTO> convertToContactsFromSFDCContactsList(final List<SFDCContactsDTO> sfdcContactsDTOs);
}
