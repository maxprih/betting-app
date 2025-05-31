package org.bebra.balanceservice.web;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author max_pri
 */
@FeignClient(name = "main-service", url = "http://backend:8080")
public interface MainClient {
    @PostMapping("/api/v1/transaction")
    void createNewTransaction(@RequestParam Integer userId, @RequestParam Integer amount);
}
