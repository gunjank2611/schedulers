package com.thermax.cp.salesforce.dto.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ErpOrderStatusDTO {

    @JsonProperty("Total Count")
    private long totalCount;

    @JsonProperty("Page Number")
    private long pageNumber;

    @JsonProperty("Return")
    private List<SFDCOrderHeadersDTO> ordersList;
}
