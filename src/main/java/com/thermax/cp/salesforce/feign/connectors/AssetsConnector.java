package com.thermax.cp.salesforce.feign.connectors;

import com.thermax.cp.salesforce.dto.utils.FileURLDTO;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "AssetsConnector", url = "${feign.client.assets.base-url}")
public interface AssetsConnector {
    @RateLimiter(name = "commonClientRateLimit", fallbackMethod = "rateLimitFallBack")
    @CircuitBreaker(name = "commonClientCB", fallbackMethod = "circuitBreakerFallback")
    @PostMapping(value = "${feign.client.assets.recommendations-url}")
    ResponseEntity<Void> sendRecommendationBlobUrl(@RequestBody FileURLDTO fileURLDTO);

    @RateLimiter(name = "commonClientRateLimit", fallbackMethod = "rateLimitFallBack")
    @CircuitBreaker(name = "commonClientCB", fallbackMethod = "circuitBreakerFallback")
    @PostMapping(value = "${feign.client.assets.assets-url}")
    ResponseEntity<Void> sendAssetsBlobUrl(@RequestBody FileURLDTO fileURLDTO);



    default ResponseEntity<String> circuitBreakerFallback(Exception e) {
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
                .body("Assets service is currently unavailable, please try again after sometime!");

    }

    default ResponseEntity<String> rateLimitFallBack(Throwable throwable) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body("Too many requests for retrieving asset list: Please try again after sometime");
    }
}
