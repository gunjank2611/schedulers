package com.thermax.cp.salesforce.dto.spares;

import lombok.Data;

import java.util.List;
@Data
public class SFDCSparesListDTO {
    private String totalSize;
    private String done;
    private String nextRecordsUrl;
    private List<SFDCSparesDTO> records;
}
