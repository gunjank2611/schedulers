package com.thermax.cp.salesforce.feign.request;

import com.thermax.cp.salesforce.dto.upload.FileUploadResponseDTO;
import com.thermax.cp.salesforce.entity.FileEntity;
import com.thermax.cp.salesforce.feign.config.FeignRequestConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "SfdcFileUploaderFeignClient", url = "${feign.client.salesforce-url}", configuration = FeignRequestConfiguration.class)
public interface SfdcFileUploadRequest {

    @PostMapping("/data/v52.0/sobjects/ContentVersion")
    ResponseEntity<FileUploadResponseDTO> uploadFileToSfdc(FileEntity fileEntity);
}
