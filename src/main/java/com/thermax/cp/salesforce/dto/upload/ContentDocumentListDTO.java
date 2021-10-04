package com.thermax.cp.salesforce.dto.upload;

import lombok.Data;

import java.util.List;

@Data
public class ContentDocumentListDTO {

    public int totalSize;
    public boolean done;
    public List<ContentDocumentRecordDTO> records;
}
