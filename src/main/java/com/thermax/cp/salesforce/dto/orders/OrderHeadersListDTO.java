package com.thermax.cp.salesforce.dto.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class OrderHeadersListDTO {

    @JsonProperty("Return")
    private List<OrderHeadersDTO> ordersList;
}
