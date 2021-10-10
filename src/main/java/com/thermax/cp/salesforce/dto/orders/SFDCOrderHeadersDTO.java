package com.thermax.cp.salesforce.dto.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class SFDCOrderHeadersDTO {
    @JsonProperty("order_number")
    private String orderNumber;
    @JsonProperty("Header Status")
    private String headerStatus;
    @JsonProperty("Expected Delivery Date")
    private String expectedDeliveryDate;
}
