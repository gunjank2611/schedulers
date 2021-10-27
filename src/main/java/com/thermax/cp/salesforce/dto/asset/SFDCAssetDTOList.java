package com.thermax.cp.salesforce.dto.asset;

import lombok.Data;

import java.util.List;

@Data
public class SFDCAssetDTOList {
    private String totalSize;
    private String done;
    private String nextRecordsUrl;
    private List<SFDCAssetDTO> records;
}
