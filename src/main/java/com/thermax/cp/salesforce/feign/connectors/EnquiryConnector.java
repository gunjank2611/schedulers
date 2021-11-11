package com.thermax.cp.salesforce.feign.connectors;

import com.thermax.cp.salesforce.dto.utils.FileURLDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "EnquiryConnector", url = "${feign.client.enquiry.base-url}")
public interface EnquiryConnector {
    @RateLimiter(name = "commonClientRateLimit", fallbackMethod = "rateLimitFallBack")
    @CircuitBreaker(name = "commonClientCB", fallbackMethod = "circuitBreakerFallback")
    @PostMapping(value = "${feign.client.enquiry.orders-url}")
    ResponseEntity<Void> sendOrdersBlobUrl(@RequestBody FileURLDTO fileURLDTO);

    @RateLimiter(name = "commonClientRateLimit", fallbackMethod = "rateLimitFallBack")
    @CircuitBreaker(name = "commonClientCB", fallbackMethod = "circuitBreakerFallback")
    @PostMapping(value = "${feign.client.enquiry.order-items-url}")
    ResponseEntity<Void> sendOrderItemsBlobUrl(@RequestBody FileURLDTO fileURLDTO);


    @RateLimiter(name = "commonClientRateLimit", fallbackMethod = "rateLimitFallBack")
    @CircuitBreaker(name = "commonClientCB", fallbackMethod = "circuitBreakerFallback")
    @PostMapping(value = "${feign.client.enquiry.order-status-url}")
    ResponseEntity<Void> sendOrderStatusBlobUrl(@RequestBody FileURLDTO fileURLDTO);

    @RateLimiter(name = "commonClientRateLimit", fallbackMethod = "rateLimitFallBack")
    @CircuitBreaker(name = "commonClientCB", fallbackMethod = "circuitBreakerFallback")
    @PostMapping(value = "${feign.client.enquiry.opportunity-contact-role-url}")
    ResponseEntity<Void> sendOpportunityContactRoleBlobUrl(@RequestBody FileURLDTO fileURLDTO);

    @RateLimiter(name = "commonClientRateLimit", fallbackMethod = "rateLimitFallBack")
    @CircuitBreaker(name = "commonClientCB", fallbackMethod = "circuitBreakerFallback")
    @PostMapping(value = "${feign.client.enquiry.thermax-users-url}")
    ResponseEntity<Void> sendThermaxUsersUrl(@RequestBody FileURLDTO fileURLDTO);

    @RateLimiter(name = "commonClientRateLimit", fallbackMethod = "rateLimitFallBack")
    @CircuitBreaker(name = "commonClientCB", fallbackMethod = "circuitBreakerFallback")
    @PostMapping(value = "${feign.client.enquiry.complaints-url}")
    ResponseEntity<Void> sendComplaintsUrl(@RequestBody FileURLDTO fileURLDTO);

    @RateLimiter(name = "commonClientRateLimit", fallbackMethod = "rateLimitFallBack")
    @CircuitBreaker(name = "commonClientCB", fallbackMethod = "circuitBreakerFallback")
    @PostMapping(value = "${feign.client.enquiry.eligible-spares-service-url}")
    ResponseEntity<Void> sendEligibleSparesServiceUrl(@RequestBody FileURLDTO fileURLDTO);

    @RateLimiter(name = "commonClientRateLimit", fallbackMethod = "rateLimitFallBack")
    @CircuitBreaker(name = "commonClientCB", fallbackMethod = "circuitBreakerFallback")
    @PostMapping(value = "${feign.client.enquiry.pricebook-url}")
    ResponseEntity<Void> sendPricebookUrl(@RequestBody FileURLDTO fileURLDTO);

    @RateLimiter(name = "commonClientRateLimit", fallbackMethod = "rateLimitFallBack")
    @CircuitBreaker(name = "commonClientCB", fallbackMethod = "circuitBreakerFallback")
    @PostMapping(value = "${feign.client.enquiry.pricebook-entry-url}")
    ResponseEntity<Void> sendPricebookEntries(@RequestBody FileURLDTO fileURLDTO);

    @RateLimiter(name = "commonClientRateLimit", fallbackMethod = "rateLimitFallBack")
    @CircuitBreaker(name = "commonClientCB", fallbackMethod = "circuitBreakerFallback")
    @PostMapping(value = "${feign.client.enquiry.opportunities-url}")
    ResponseEntity<Void> sendOpportunities(@RequestBody FileURLDTO fileURLDTO);

    @RateLimiter(name = "commonClientRateLimit", fallbackMethod = "rateLimitFallBack")
    @CircuitBreaker(name = "commonClientCB", fallbackMethod = "circuitBreakerFallback")
    @PostMapping(value = "${feign.client.enquiry.opportunity-line-item-url}")
    ResponseEntity<Void> sendOpportunityLineItems(@RequestBody FileURLDTO fileURLDTO);

    default ResponseEntity<String> circuitBreakerFallback(Exception e) {
        return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
                .body("Enquiry service is currently unavailable, please try again after sometime: " + e.getMessage());

    }

    default ResponseEntity<String> rateLimitFallBack(Throwable throwable) {
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body("Too many requests for sending orders data: Please try again after sometime: " + throwable.getMessage());
    }
}
