package com.thermax.cp.salesforce.config;

import com.thermax.cp.salesforce.dto.OAuthResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "SfdcOAuthConnectorFeignClient", url = "${feign.client.salesforce-url}", configuration = OAuthConfiguration.class)
public interface SfdcOAuthConnectorRequest {

    @PostMapping("/oauth2/token")
    OAuthResponseDTO getAuthentication(
            @RequestParam("grant_type") String grantType,
            @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret,
            @RequestParam("username") String username,
            @RequestParam("password") String password
    );
}
