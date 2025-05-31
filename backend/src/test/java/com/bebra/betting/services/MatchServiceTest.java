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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatchServiceTest {

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private CountryRepository countryRepository;

    @Mock
    private LeagueRepository leagueRepository;

    @Mock
    private MatchEventRepository matchEventRepository;

    @Mock
    private PokemonRepository pokemonRepository;

    @Mock
    private MatchMapper matchMapper;

    @InjectMocks
    private MatchService matchService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllMatches_Success() {
        Match match = new Match();
        MatchDto matchDto = new MatchDto();

        when(matchRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(List.of(match)));
        when(matchMapper.toDto(match)).thenReturn(matchDto);

        Page<MatchDto> result = matchService.getAllMatches(PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(matchRepository, times(1)).findAll(any(PageRequest.class));
        verify(matchMapper, times(1)).toDto(match);
    }

    @Test
    void testCreateMatch_Success() {
        CreateMatchRequest request = new CreateMatchRequest();
        request.setLeagueId(1);
        request.setCountryId(2);
        request.setFirstPlayerId(3);
        request.setSecondPlayerId(4);
        request.setDate(LocalDateTime.of(2025, 1, 1, 12, 0));

        League league = new League();
        Country country = new Country();
        Pokemon firstPlayer = new Pokemon();
        Pokemon secondPlayer = new Pokemon();

        when(leagueRepository.findById(request.getLeagueId())).thenReturn(Optional.of(league));
        when(countryRepository.findById(request.getCountryId())).thenReturn(Optional.of(country));
        when(pokemonRepository.findById(request.getFirstPlayerId())).thenReturn(Optional.of(firstPlayer));
        when(pokemonRepository.findById(request.getSecondPlayerId())).thenReturn(Optional.of(secondPlayer));

        matchService.create(request);

        ArgumentCaptor<Match> matchCaptor = ArgumentCaptor.forClass(Match.class);
        verify(matchRepository, times(1)).save(matchCaptor.capture());

        Match savedMatch = matchCaptor.getValue();
        assertNotNull(savedMatch);
        assertEquals(league, savedMatch.getLeague());
        assertEquals(country, savedMatch.getCountry());
        assertEquals(firstPlayer, savedMatch.getFirstPlayer());
        assertEquals(secondPlayer, savedMatch.getSecondPlayer());
        assertEquals(Instant.parse("2025-01-01T12:00:00Z"), savedMatch.getDate());
    }

    @Test
    void testCreateMatch_LeagueNotFound() {
        CreateMatchRequest request = new CreateMatchRequest();
        request.setLeagueId(1);

        when(leagueRepository.findById(request.getLeagueId())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> matchService.create(request));
        verify(matchRepository, never()).save(any());
    }

    @Test
    void testGetMatchById_Success() {
        int matchId = 1;

        MatchDto matchDto = new MatchDto();
        matchDto.setId(matchId);

        when(matchRepository.findMatchById(matchId)).thenReturn(matchDto);
        when(matchEventRepository.findMatchEventsByMatchId(matchId)).thenReturn(Set.of());

        MatchDto result = matchService.getMatchById(matchId);

        assertNotNull(result);
        assertEquals(matchId, result.getId());
        assertNotNull(result.getMatchEvents());
        verify(matchRepository, times(1)).findMatchById(matchId);
        verify(matchEventRepository, times(1)).findMatchEventsByMatchId(matchId);
    }

    @Test
    void testGetMatchById_NotFound() {
        int matchId = 1;

        when(matchRepository.findMatchById(matchId)).thenReturn(null);

        assertThrows(RuntimeException.class, () ->matchService.getMatchById(matchId));

        verify(matchRepository, times(1)).findMatchById(matchId);
    }
}
