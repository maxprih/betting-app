package com.bebra.betting.services;

import com.bebra.betting.exceptions.AppError;
import com.bebra.betting.models.entity.Bet;
import com.bebra.betting.models.entity.MatchEvent;
import com.bebra.betting.models.entity.User;
import com.bebra.betting.repositories.BetRepository;
import com.bebra.betting.repositories.MatchEventRepository;
import org.bebra.dto.BalanceDto;
import org.bebra.dto.BetDto;
import org.bebra.dto.MatchEventDto;
import org.bebra.dto.requests.MakeBetRequest;
import org.bebra.enums.BetStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BetServiceTest {

    @Mock
    private BetRepository betRepository;

    @Mock
    private BalanceService balanceService;

    @Mock
    private MatchEventRepository matchEventRepository;

    @Mock
    private UserRetrievalService userRetrievalService;

    @InjectMocks
    private BetService betService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBets_Success() {
        User user = new User();
        user.setId(1);

        Bet bet = Bet.builder()
                .id(1)
                .amount(100)
                .status(BetStatus.TBD)
                .date(Instant.now())
                .user(user)
                .build();
        List<Bet> bets = List.of(bet);
        Page<Bet> betPage = new PageImpl<>(bets);

        MatchEventDto matchEventDto = new MatchEventDto();
        matchEventDto.setCoefficient(1.5);

        when(userRetrievalService.getUserFromContext()).thenReturn(user);
        when(betRepository.findAllByUserId(any(PageRequest.class), eq(user.getId()))).thenReturn(betPage);
        when(matchEventRepository.findByBetId(bet.getId())).thenReturn(Set.of(matchEventDto));
        when(betRepository.getBetWinning(bet.getId())).thenReturn(150.0);

        Page<BetDto> result = betService.getAllBets(PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1.5, result.getContent().get(0).getCoefficient());
        assertEquals(150.0, result.getContent().get(0).getWinnings());
    }

    @Test
    void testMakeBet_Success() {
        User user = new User();
        user.setId(1);
        user.setLogin("test_user");

        MakeBetRequest request = new MakeBetRequest();
        request.setAmount(100);
        request.setIds(Set.of(1, 2));

        MatchEvent matchEvent1 = new MatchEvent();
        matchEvent1.setId(1);

        MatchEvent matchEvent2 = new MatchEvent();
        matchEvent2.setId(2);

        when(userRetrievalService.getUserFromContext()).thenReturn(user);
        when(balanceService.getBalanceForUser(user)).thenReturn(new BalanceDto(1, 1, 200));
        when(matchEventRepository.findAllByIdIn(request.getIds())).thenReturn(Set.of(matchEvent1, matchEvent2));

        doReturn(new BalanceDto()).when(balanceService).changeUserBalance(user, -request.getAmount());

        betService.makeBet(request);

        ArgumentCaptor<Bet> betCaptor = ArgumentCaptor.forClass(Bet.class);
        verify(betRepository).save(betCaptor.capture());

        Bet savedBet = betCaptor.getValue();
        assertNotNull(savedBet);
        assertEquals(100, savedBet.getAmount());
        assertEquals(BetStatus.TBD, savedBet.getStatus());
        assertEquals(user, savedBet.getUser());
        assertEquals(2, savedBet.getMatchEvents().size());
    }

    @Test
    void testMakeBet_InsufficientFunds() {
        User user = new User();
        user.setId(1);

        MakeBetRequest request = new MakeBetRequest();
        request.setAmount(200);

        when(userRetrievalService.getUserFromContext()).thenReturn(user);
        when(balanceService.getBalanceForUser(user)).thenReturn(new BalanceDto(1, 1, 100));

        AppError exception = assertThrows(AppError.class, () -> betService.makeBet(request));

        assertEquals("Недостаточно денег", exception.getMessage());
        verify(betRepository, never()).save(any());
        verify(balanceService, never()).changeUserBalance(any(), anyInt());
    }
}
