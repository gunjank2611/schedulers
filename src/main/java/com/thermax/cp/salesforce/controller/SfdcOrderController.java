package com.thermax.cp.salesforce.controller;

import com.thermax.cp.salesforce.dto.orders.OrderHeadersDTO;
import com.thermax.cp.salesforce.dto.orders.OrderHeadersListDTO;
import com.thermax.cp.salesforce.dto.orders.OrderIdDTO;
//import com.thermax.cp.salesforce.dto.orders.OrderIdsRequest;
import com.thermax.cp.salesforce.feign.request.SfdcOrdersRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/sfdc/schedulers")

public class SfdcOrderController {
    @Autowired
    private SfdcOrdersRequest sfdcOrdersRequest;

    @PostMapping(value = "/getOrderHeaders" , consumes = "application/json")
    @ResponseStatus(value = HttpStatus.OK, reason = "Products loaded successfully")
    public ResponseEntity<OrderHeadersListDTO> getOrders(@RequestBody List<OrderIdDTO> orderIdsList)  {
        return sfdcOrdersRequest.getOrders(orderIdsList);

    }
}
