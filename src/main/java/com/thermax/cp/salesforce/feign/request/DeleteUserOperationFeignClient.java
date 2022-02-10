package com.thermax.cp.salesforce.feign.request;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "DeleteUserOperationFeignClient", url = "${feign.client.contacts.base-url}")
public interface DeleteUserOperationFeignClient {

    @GetMapping("${feign.client.contacts.delete-operation-url}")
    void deleteContacts();
}
