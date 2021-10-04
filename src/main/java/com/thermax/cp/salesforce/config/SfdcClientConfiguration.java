package com.thermax.cp.salesforce.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(value = "sfdc")
public class SfdcClientConfiguration {

    private String grantType;
    private String clientId;
    private String clientSecret;
    private String username;
    private String password;
}
