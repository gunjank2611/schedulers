package com.thermax.cp.salesforce.feign.request;

import com.thermax.cp.salesforce.dto.asset.history.AssetHistoryRecordListDTO;
import com.thermax.cp.salesforce.dto.orders.OrderHeadersDTO;
import com.thermax.cp.salesforce.dto.orders.OrderIdDTO;
import com.thermax.cp.salesforce.dto.orders.OrderIdsRequest;
import com.thermax.cp.salesforce.dto.orders.SFDCOrdersDTO;
import com.thermax.cp.salesforce.feign.config.FeignRequestConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "SfdcOrdersRequestClient", url = "${feign.client.orderHeadersUrl}", configuration = FeignRequestConfiguration.class)
public interface SfdcOrdersRequest {

    @PostMapping(value = "api/cp/get_order_status/",produces = "application/json")
    ResponseEntity<OrderHeadersDTO> getOrders(@RequestBody List<OrderIdDTO> ordersDTO);
}