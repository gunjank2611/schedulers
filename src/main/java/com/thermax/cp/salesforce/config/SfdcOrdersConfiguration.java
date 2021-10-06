package com.thermax.cp.salesforce.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(value = "sfdcorders")
public class SfdcOrdersConfiguration {
    private String username;
    private String password;
    private String instance;
}
