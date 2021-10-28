package com.thermax.cp.salesforce.dto.orders;

import lombok.Data;

import java.util.List;

@Data
public class SFDCOrdersItemsListDTO {
    private String totalSize;
    private String done;
    private List<SFDCOrderItemsDTO> records;
    private String nextRecordsUrl;
}
