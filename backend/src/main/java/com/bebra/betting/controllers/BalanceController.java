package com.bebra.betting.controllers;

import com.bebra.betting.services.BalanceService;
import org.bebra.dto.requests.WithdrawRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author max_pri
 */
@RestController
@RequestMapping("api/v1/balance")
@CrossOrigin(origins = "http://localhost:8081")
public class BalanceController {
    private final BalanceService balanceService;

    @Autowired
    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @PostMapping("/free")
    public ResponseEntity<?> addFreeMoney() {
        balanceService.addFreeMoney();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody WithdrawRequest request) {
        balanceService.withdraw(request);
        return ResponseEntity.ok().build();
    }
}
