package org.bebra.balanceservice.controllers;

import org.bebra.balanceservice.services.BalanceService;
import org.bebra.dto.BalanceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author max_pri
 */
@RestController
@RequestMapping("api/v1/balance")
@CrossOrigin(origins = "http://localhost:8080")
public class BalanceController {
    private final BalanceService balanceService;

    @Autowired
    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @PostMapping("/change")
    public ResponseEntity<?> changeUserBalance(@RequestParam Integer userId, @RequestParam Integer amount) {
        return ResponseEntity.ok(balanceService.changeUserBalance(userId, amount));
    }

    @PostMapping
    public ResponseEntity<?> createBalance(@RequestParam Integer userId) {
        return ResponseEntity.ok(balanceService.createBalanceForUser(userId));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<BalanceDto> getBalanceForUser(@PathVariable Integer userId) {
        return ResponseEntity.ok(balanceService.getBalanceForUser(userId));
    }
}
