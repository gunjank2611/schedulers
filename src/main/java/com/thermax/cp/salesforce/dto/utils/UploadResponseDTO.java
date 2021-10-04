package com.thermax.cp.salesforce.dto.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UploadResponseDTO {
    @JsonProperty("azure_error")
    private String azureError;
    @JsonProperty("azure_blob_url")
    private String azureBlobUrl;
}
