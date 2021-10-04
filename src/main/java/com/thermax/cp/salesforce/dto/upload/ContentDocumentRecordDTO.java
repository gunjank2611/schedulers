package com.thermax.cp.salesforce.dto.upload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ContentDocumentRecordDTO {

    public ContentDocumentAttributesDTO attributes;

    @JsonProperty("ContentDocumentId")
    public String contentDocumentId;
}
