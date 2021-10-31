package com.thermax.cp.salesforce.mapper;

import com.thermax.cp.salesforce.dto.orders.OrderHeadersDTO;
import com.thermax.cp.salesforce.dto.orders.OrdersDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOrderHeadersDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOrdersDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface OrdersMapper {
    List<OrderHeadersDTO> convertToTOrderHeadersDTOList(final List<SFDCOrderHeadersDTO> orderHeadersDTOS);

    @Mapping(source = "headerStatus", target = "erpStatus")
    @Mapping(source = "orderNumber", target = "orderNumber")
    @Mapping(source = "expectedDeliveryDate", target = "edd")
    OrderHeadersDTO convertToTOrderHeadersDTO(final SFDCOrderHeadersDTO orderHeadersDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "orderNumber", target = "orderNumber")
    @Mapping(source = "THCMG_Customer_PO__c", target = "THCMG_Customer_PO__c")
    @Mapping(source = "accountId", target = "accountId")
    @Mapping(source = "THCMG_Payment_Term__c", target = "THCMG_Payment_Term__c")
    @Mapping(source = "THCMG_ERP_Operating_Unit__c", target = "THCMG_ERP_Operating_Unit__c")
    @Mapping(source = "THCMG_Cheque_Number__c", target = "THCMG_Cheque_Number__c")
    @Mapping(source = "THCMG_Transaction_Type_Id__c", target = "THCMG_Transaction_Type_Id__c")
    @Mapping(source = "THCMG_Bill_To_Location__c", target = "THCMG_Bill_To_Location__c")
    @Mapping(source = "THCMG_Warehouse__c", target = "THCMG_Warehouse__c")
    @Mapping(source = "THCMG_Ship_To_Location__c", target = "THCMG_Ship_To_Location__c")
    @Mapping(source = "THCMG_Date_Ordered__c", target = "THCMG_Date_Ordered__c")
    @Mapping(source = "THCMG_Payment_Type__c", target = "THCMG_Payment_Type__c")
    @Mapping(source = "THCMG_Request_Date__c", target = "THCMG_Request_Date__c")
    @Mapping(source = "effectiveDate", target = "effectiveDate")
    @Mapping(source = "totalAmount", target = "totalAmount")
    @Mapping(source = "THCMG_FOB__c", target = "THCMG_FOB__c")
    @Mapping(source = "TH_Division__c", target = "TH_Division__c")
    @Mapping(source = "THCMG_Freight_Terms__c", target = "THCMG_Freight_Terms__c")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "opportunityId", target = "opportunityId")
    @Mapping(source = "ERP_Order_Number__c", target = "ERP_Order_Number__c")
    @Mapping(source = "TH_Opportunity_Number__c", target = "TH_Opportunity_Number__c")
    @Mapping(source = "asset__c", target = "asset__c")
    @Mapping(source = "opportunity.ownerId", target = "ownerId")
    @Mapping(source = "opportunity.owner.ownerName", target = "ownerName")
    @Mapping(source = "opportunity.owner.email", target = "ownerEmail")
    @Mapping(source = "opportunity.owner.mobilePhone", target = "ownerContact")
    @Mapping(source = "opportunity.owner.userRole.userRoleName", target = "ownerRole")
    @Mapping(source = "opportunity.opportunityType", target = "opportunityType")
    OrdersDTO convertToOrdersFromSFDCOrders(final SFDCOrdersDTO sfdcOrdersDTO);

    List<OrdersDTO> convertToOrdersFromSFDCOrdersList(final List<SFDCOrdersDTO> sfdcOrdersDTO);
}
