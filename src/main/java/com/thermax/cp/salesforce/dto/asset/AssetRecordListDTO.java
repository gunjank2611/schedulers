package com.thermax.cp.salesforce.dto.asset;

import lombok.Data;

import java.util.List;

@Data
public class AssetRecordListDTO {

    private int totalSize;
    private boolean done;
    private List<AssetRecordDTO> records;
}
