package com.thermax.cp.salesforce.feign.config;

import com.thermax.cp.salesforce.config.SfdcClientConfiguration;
import com.thermax.cp.salesforce.config.SfdcOAuthConnectorRequest;
import com.thermax.cp.salesforce.config.SfdcOrdersConfiguration;
import feign.RequestInterceptor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
public class FeignRequestConfiguration {

    @Autowired
    private SfdcOAuthConnectorRequest sfdcOAuthConnectorRequest;

    @Autowired
    private SfdcClientConfiguration sfdcClientConfiguration;

    @Autowired
    private SfdcOrdersConfiguration sfdcOrdersConfiguration;

    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;


    @Bean
    RequestInterceptor buildRequestInterceptor() {
        log.info("Inside buildRequest Interceptor()..");
        return requestTemplate -> {
            String requestUrl = requestTemplate.url();
            log.info("[RequestInterceptor] -> requestURL :: " + requestUrl);
            if (requestUrl.contains("/oauth2/token")) {
                log.info("Excluding request from interceptor for the url : " + requestUrl);
                return;
            } else if (requestUrl.contains("/api/v1/upload/")) {
                log.info("keeping default multipart header for the file upload url : " + requestUrl);
            } else if (requestUrl.contains("/api/cp/get_order_status")) {
                requestTemplate.header("USERNAME", sfdcOrdersConfiguration.getUsername());
                requestTemplate.header("PASSWORD", sfdcOrdersConfiguration.getPassword());
                requestTemplate.header("INSTANCE", sfdcOrdersConfiguration.getInstance());
                return;
            } else {
                requestTemplate.header("Content-Type", "application/json");
                requestTemplate.header("Accept", "application/json");
            }
            requestTemplate.header("Authorization", "Bearer " + getBearerAccessTokenFromSfdc());
        };
    }

    /**
     * This will return the bearer access-token from sfdc authenticator.
     *
     * @return
     */
    private String getBearerAccessTokenFromSfdc() {
        return sfdcOAuthConnectorRequest.getAuthentication(sfdcClientConfiguration.getGrantType(),
                        sfdcClientConfiguration.getClientId(),
                        sfdcClientConfiguration.getClientSecret(),
                        sfdcClientConfiguration.getUsername(),
                        sfdcClientConfiguration.getPassword())
                .getAccess_token();


    }

}
