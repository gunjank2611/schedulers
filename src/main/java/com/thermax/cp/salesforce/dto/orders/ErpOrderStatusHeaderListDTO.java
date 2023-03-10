package com.thermax.cp.salesforce.dto.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ErpOrderStatusHeaderListDTO {

    @JsonProperty("Return")
    private List<SFDCOrderHeadersDTO> ordersList; // Change name to ErpOrderStatusHeaderDTO
}
