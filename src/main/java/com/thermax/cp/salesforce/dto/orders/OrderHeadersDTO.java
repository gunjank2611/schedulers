package com.thermax.cp.salesforce.dto.orders;

import lombok.Data;


@Data
public class OrderHeadersDTO {
    private String orderNumber;
    private String headerStatus;
    private String expectedDeliveryDate;
}