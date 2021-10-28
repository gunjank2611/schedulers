package com.thermax.cp.salesforce.dto.pricebook;


import lombok.Data;

import java.util.List;

@Data
public class SFDCPricebookDTOList {
    private String totalSize;
    private String done;
    private String nextRecordsUrl;
    private List<SFDCPricebookDTO> records;
}
