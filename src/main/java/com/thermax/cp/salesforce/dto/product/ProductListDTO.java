package com.thermax.cp.salesforce.dto.product;

import lombok.Data;

import java.util.List;

@Data
public class ProductListDTO {

    private String totalSize;
    private String done;
    private String nextRecordsUrl;
    private List<SFDCProductInfoDTO> records;
}
