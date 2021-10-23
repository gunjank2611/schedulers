package com.thermax.cp.salesforce.feign.request;

import com.thermax.cp.salesforce.dto.orders.SFDCOrderHeadersListDTO;
import com.thermax.cp.salesforce.dto.orders.OrderIdDTO;
import com.thermax.cp.salesforce.feign.config.FeignRequestConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@FeignClient(name = "SfdcOrdersRequestClient", url = "${feign.client.order-status.base-url}", configuration = FeignRequestConfiguration.class)
public interface SfdcOrdersRequest {

    @PostMapping(value = "${feign.client.order-status.get-status}")
    ResponseEntity<SFDCOrderHeadersListDTO> fetchOrderStatus(@RequestBody List<OrderIdDTO> ordersDTO);
}