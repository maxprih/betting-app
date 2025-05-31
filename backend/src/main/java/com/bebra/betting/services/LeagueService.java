package com.bebra.betting.services;

import com.bebra.betting.models.entity.League;
import com.bebra.betting.repositories.LeagueRepository;
import org.bebra.dto.requests.CreateLeagueRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author max_pri
 */
@Service
public class LeagueService {
    private final LeagueRepository leagueRepository;

    @Autowired
    public LeagueService(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    public void createLeague(CreateLeagueRequest request) {
        League league = new League();
        league.setName(request.getName());
        league.setStartDate(request.getStartDate());
        league.setEndDate(request.getEndDate());

        leagueRepository.save(league);
    }
}
