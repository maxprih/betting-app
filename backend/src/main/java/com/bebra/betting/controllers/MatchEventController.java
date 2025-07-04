package com.bebra.betting.controllers;

import com.bebra.betting.services.MatchEventService;
import org.bebra.dto.requests.ChangeEventRequest;
import org.bebra.dto.requests.CreateMatchEventRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author max_pri
 */
@RestController
@RequestMapping("api/v1/events")
public class MatchEventController {
    private final MatchEventService matchEventService;

    @Autowired
    public MatchEventController(MatchEventService matchEventService) {
        this.matchEventService = matchEventService;
    }

    @PutMapping("/change")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> changeEventStatus(@RequestBody ChangeEventRequest request) {
        matchEventService.changeEventStatus(request);

        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateMatchEventRequest createMatchEventRequest) {
        matchEventService.create(createMatchEventRequest);

        return ResponseEntity.ok().build();
    }
}
