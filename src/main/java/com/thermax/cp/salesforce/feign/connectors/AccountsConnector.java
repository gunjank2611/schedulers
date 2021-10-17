package com.thermax.cp.salesforce.feign.connectors;

import com.thermax.cp.salesforce.dto.utils.FileURLDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "AccountsConnector", url = "${feign.client.accounts.base-Url}")
public interface AccountsConnector {
    @RateLimiter(name = "commonClientRateLimit", fallbackMethod = "rateLimitFallBack")
    @CircuitBreaker(name = "commonClientCB", fallbackMethod = "circuitBreakerFallback")
    @PostMapping(value = "${feign.client.accounts.accounts-url}")
    ResponseEntity<Void> sendAccountBlobUrl(@RequestBody FileURLDTO fileURLDTO);

    default ResponseEntity<String> circuitBreakerFallback(Exception e) {
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
                .body("Accounts service is currently unavailable, please try again after sometime!");

    }

    default ResponseEntity<String> rateLimitFallBack(Throwable throwable) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body("Too many requests for retrieving account list: Please try again after sometime");
    }
}
