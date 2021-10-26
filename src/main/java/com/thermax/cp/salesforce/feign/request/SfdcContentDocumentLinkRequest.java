package com.thermax.cp.salesforce.feign.request;

import com.thermax.cp.salesforce.dto.upload.LinkedContentDocumentToEntityDTO;
import com.thermax.cp.salesforce.entity.ContentLinkEntity;
import com.thermax.cp.salesforce.feign.config.FeignRequestConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "SfdcContentDocumentLinkFeignClient", url = "${feign.client.salesforce-url}", configuration = FeignRequestConfiguration.class)
public interface SfdcContentDocumentLinkRequest {

    @PostMapping("/data/v52.0/sobjects/ContentDocumentLink")
    ResponseEntity<LinkedContentDocumentToEntityDTO> linkContentDocumentWithEntity(ContentLinkEntity contentLinkEntity);
}
