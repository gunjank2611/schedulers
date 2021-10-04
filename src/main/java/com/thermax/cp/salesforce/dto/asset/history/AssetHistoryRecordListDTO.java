package com.thermax.cp.salesforce.dto.asset.history;

import lombok.Data;

import java.util.List;

@Data
public class AssetHistoryRecordListDTO {

    private String totalSize;
    private String done;
    private List<AssetHistoryRecordDTO> records;
}
