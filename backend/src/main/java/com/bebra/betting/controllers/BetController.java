package com.bebra.betting.controllers;

import com.bebra.betting.services.BetService;
import org.bebra.dto.BetDto;
import org.bebra.dto.requests.MakeBetRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author max_pri
 */
@RestController
@RequestMapping("api/v1/bets")
public class BetController {
    private final BetService betService;

    @Autowired
    public BetController(BetService betService) {
        this.betService = betService;
    }

    @PostMapping()
    public ResponseEntity<?> makeBet(@RequestBody MakeBetRequest request) {
        betService.makeBet(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Page<BetDto>> getAllBets(Pageable pageable) {
        return ResponseEntity.ok(betService.getAllBets(pageable));
    }

}
