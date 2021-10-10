package com.thermax.cp.salesforce.mapper;

import com.thermax.cp.salesforce.dto.orders.OrderHeadersDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOrderHeadersDTO;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface OrdersMapper {
    List<OrderHeadersDTO> convertToTOrderHeadersDTOList(final List<SFDCOrderHeadersDTO> orderHeadersDTOS);
}
