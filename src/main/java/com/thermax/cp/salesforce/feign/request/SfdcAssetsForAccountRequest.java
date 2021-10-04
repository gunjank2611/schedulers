package com.thermax.cp.salesforce.feign.request;

import com.thermax.cp.salesforce.dto.asset.AssetRecordListDTO;
import com.thermax.cp.salesforce.feign.config.FeignRequestConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "SfdcAssetsForAccountRequestFeignClient", url = "${feign.client.url}", configuration = FeignRequestConfiguration.class)
public interface SfdcAssetsForAccountRequest {

    @GetMapping("/data/v52.0/query/?q={query}")
    ResponseEntity<AssetRecordListDTO> getAssetsForAccount(@RequestParam(value = "q") String query);
}
