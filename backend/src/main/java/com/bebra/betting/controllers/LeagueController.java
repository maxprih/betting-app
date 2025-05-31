package com.bebra.betting.controllers;

import com.bebra.betting.repositories.LeagueRepository;
import com.bebra.betting.services.LeagueService;
import org.bebra.dto.LeagueDto;
import org.bebra.dto.requests.CreateLeagueRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author max_pri
 */
@RestController
@RequestMapping("api/v1/league")
public class LeagueController {
    private final LeagueRepository leagueRepository;
    private final LeagueService leagueService;

    @Autowired
    public LeagueController(LeagueRepository leagueRepository, LeagueService leagueService) {
        this.leagueRepository = leagueRepository;
        this.leagueService = leagueService;
    }

    @GetMapping()
    public ResponseEntity<List<LeagueDto>> getAll() {
        return ResponseEntity.ok(leagueRepository.getAll());
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody CreateLeagueRequest request) {
        leagueService.createLeague(request);
        return ResponseEntity.ok().build();
    }
}
