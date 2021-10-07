package com.thermax.cp.salesforce.dto.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;



@Data
public class OrderHeadersDTO {
    @JsonProperty("Header Status")
    private String headerStatus;
    @JsonProperty("Expected Delivery Date")
    private String expectedDeliveryDate;
}
