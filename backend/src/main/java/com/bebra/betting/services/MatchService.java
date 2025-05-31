package com.bebra.betting.services;

import com.bebra.betting.models.entity.Country;
import com.bebra.betting.models.entity.League;
import com.bebra.betting.models.entity.Match;
import com.bebra.betting.models.entity.Pokemon;
import com.bebra.betting.repositories.CountryRepository;
import com.bebra.betting.repositories.LeagueRepository;
import com.bebra.betting.repositories.MatchEventRepository;
import com.bebra.betting.repositories.MatchRepository;
import com.bebra.betting.repositories.PokemonRepository;
import com.bebra.betting.utils.mappers.MatchMapper;
import org.bebra.dto.MatchDto;
import org.bebra.dto.requests.CreateMatchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;

/**
 * @author max_pri
 */
@Service
public class MatchService {
    private final MatchRepository matchRepository;
    private final CountryRepository countryRepository;
    private final LeagueRepository leagueRepository;
    private final MatchEventRepository matchEventRepository;
    private final PokemonRepository pokemonRepository;
    private final MatchMapper matchMapper;

    @Autowired
    public MatchService(MatchRepository matchRepository, CountryRepository countryRepository, LeagueRepository leagueRepository, MatchEventRepository matchEventRepository, PokemonRepository pokemonRepository, MatchMapper matchMapper) {
        this.matchRepository = matchRepository;
        this.countryRepository = countryRepository;
        this.leagueRepository = leagueRepository;
        this.matchEventRepository = matchEventRepository;
        this.pokemonRepository = pokemonRepository;
        this.matchMapper = matchMapper;
    }

    public Page<MatchDto> getAllMatches(Pageable pageable) {
        return matchRepository.findAll(pageable).map(matchMapper::toDto);
    }

    public void create(CreateMatchRequest createMatchRequest) {
        League league = leagueRepository.findById(createMatchRequest.getLeagueId()).get();
        Country country = countryRepository.findById(createMatchRequest.getCountryId()).get();
        Pokemon pokemon1 = pokemonRepository.findById(createMatchRequest.getFirstPlayerId()).get();
        Pokemon pokemon2 = pokemonRepository.findById(createMatchRequest.getSecondPlayerId()).get();


        Match match = new Match();
        match.setCountry(country);
        match.setLeague(league);
        match.setDate(createMatchRequest.getDate().toInstant(ZoneOffset.UTC));
        match.setFirstPlayer(pokemon1);
        match.setSecondPlayer(pokemon2);

        matchRepository.save(match);
    }

    public MatchDto getMatchById(Integer id) {
        MatchDto matchDto = matchRepository.findMatchById(id);
        matchDto.setMatchEvents(matchEventRepository.findMatchEventsByMatchId(id));
        return matchDto;
    }
}
