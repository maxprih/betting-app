package com.bebra.betting.controllers;

import com.bebra.betting.services.MatchService;
import org.bebra.dto.MatchDto;
import org.bebra.dto.requests.CreateMatchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author max_pri
 */
@RestController
@RequestMapping("api/v1/matches")
@CrossOrigin(origins = "http://localhost:8081")
public class MatchController {
    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping
    public ResponseEntity<Page<MatchDto>> getAllMatches(Pageable pageable) {
        return ResponseEntity.ok(matchService.getAllMatches(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchDto> getMatch(@PathVariable Integer id) {
        return ResponseEntity.ok(matchService.getMatchById(id));
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody CreateMatchRequest request) {
        matchService.create(request);

        return ResponseEntity.ok().build();
    }

}
