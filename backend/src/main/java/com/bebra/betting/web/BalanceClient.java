package com.bebra.betting.web;

import org.bebra.dto.BalanceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author max_pri
 */
@FeignClient(name = "balance-service", url = "http://balance:8081/api/v1/balance")
public interface BalanceClient {

    @GetMapping("/{userId}")
    BalanceDto getBalance(@PathVariable Integer userId);

    @PostMapping
    BalanceDto createBalance(@RequestParam Integer userId);

    @PostMapping("/change")
    BalanceDto changeBalance(@RequestParam Integer userId, @RequestParam Integer amount);
}
