package com.thermax.cp.salesforce.dto.recommendations;

import lombok.Data;

import java.util.List;
@Data
public class SFDCRecommendationsListDTO {
    private String totalSize;
    private String done;
    private String nextRecordsUrl;
    private List<SFDCRecommendationsDTO> records;
}
