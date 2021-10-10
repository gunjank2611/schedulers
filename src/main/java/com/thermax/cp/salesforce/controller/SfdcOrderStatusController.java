package com.thermax.cp.salesforce.controller;

import com.thermax.cp.salesforce.dto.orders.OrderIdDTO;
import com.thermax.cp.salesforce.dto.orders.SFDCOrderHeadersListDTO;
import com.thermax.cp.salesforce.feign.request.SfdcOrdersRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/sfdc/schedulers")

public class SfdcOrderStatusController {
    @Autowired
    private SfdcOrdersRequest sfdcOrdersRequest;

    @PostMapping(value = "/getOrderHeaders", consumes = "application/json")
    @ResponseStatus(value = HttpStatus.OK, reason = "Products loaded successfully")
    public ResponseEntity<SFDCOrderHeadersListDTO> getOrderStatus(@RequestBody List<OrderIdDTO> orderIdsList) {
        return sfdcOrdersRequest.getOrders(orderIdsList);

    }
}
