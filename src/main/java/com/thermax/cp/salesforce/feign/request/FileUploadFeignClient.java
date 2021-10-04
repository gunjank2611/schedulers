package com.thermax.cp.salesforce.feign.request;

import com.thermax.cp.salesforce.dto.utils.UploadResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;

import java.io.File;


@FeignClient(name = "FileUploadFeignClient", url = "${feign.client.fileUploadUrl}")
public interface FileUploadFeignClient {
    @PostMapping(value = "/api/v1/upload/{userName}", consumes = "multipart/form-data")
    ResponseEntity<UploadResponseDTO> uploadFileToAzure(@PathVariable String userName,
                                                        @RequestPart("myFile") File file);
}
