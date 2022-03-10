package com.thermax.cp.salesforce.feign.request;

import com.thermax.cp.salesforce.dto.orders.ErpOrderStatusDTO;
import com.thermax.cp.salesforce.dto.orders.PageNumberDTO;
import com.thermax.cp.salesforce.feign.config.FeignRequestConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "SfdcOrdersRequestClient", url = "${feign.client.order-status.base-url}", configuration = FeignRequestConfiguration.class)
public interface ErpOrderStatusRequest {

    @PostMapping(value = "${feign.client.order-status.get-status}")
    ResponseEntity<ErpOrderStatusDTO> fetchOrderStatus(@RequestBody PageNumberDTO pageNumberDTO);
}