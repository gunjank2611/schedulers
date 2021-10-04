package com.thermax.cp.salesforce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;



import java.util.Collections;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket getSwaggerConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/v1/sfdc/**"))
                .build().apiInfo(getApiInformation());

    }

    private ApiInfo getApiInformation() {
        
        return new ApiInfoBuilder().title("THERMAX APIs Documentation")
                .description("The REST API documentation for Thermax Customer Portal.").termsOfServiceUrl("All rights reserved to Nagarro / Thermax.")
                .contact(new Contact("Nagarro", "https://www.nagarro.com/en/", "Thermax.CustomerPortal@nagarro.com"))
                .version("1.0.0")
                .build();
    
    }
}
