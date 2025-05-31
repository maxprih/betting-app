package com.bebra.betting.services;

import com.bebra.betting.exceptions.AppError;
import com.bebra.betting.models.entity.Bet;
import com.bebra.betting.models.entity.MatchEvent;
import com.bebra.betting.models.entity.User;
import com.bebra.betting.repositories.BetRepository;
import com.bebra.betting.repositories.MatchEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.bebra.dto.BetDto;
import org.bebra.dto.MatchEventDto;
import org.bebra.dto.requests.MakeBetRequest;
import org.bebra.enums.BetStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Set;

/**
 * @author max_pri
 */
@Service
@Slf4j
public class BetService {
    private final BetRepository betRepository;
    private final BalanceService balanceService;
    private final MatchEventRepository matchEventRepository;
    private final UserRetrievalService userRetrievalService;

    @Autowired
    public BetService(BetRepository betRepository, BalanceService balanceService, MatchEventRepository matchEventRepository, UserRetrievalService userRetrievalService) {
        this.betRepository = betRepository;
        this.balanceService = balanceService;
        this.matchEventRepository = matchEventRepository;
        this.userRetrievalService = userRetrievalService;
    }

    public Page<BetDto> getAllBets(Pageable pageable) {
        User user = userRetrievalService.getUserFromContext();
        Page<Bet> bets = betRepository.findAllByUserId(pageable, user.getId());
        return bets.map(bet -> {
                    Set<MatchEventDto> matchEventDtos = matchEventRepository.findByBetId(bet.getId());
                    double productOfCoefficients = matchEventDtos.stream()
                            .mapToDouble(MatchEventDto::getCoefficient)
                            .reduce(1, (a, b) -> a * b);
                    double winnings = betRepository.getBetWinning(bet.getId());

                    return BetDto.builder()
                            .id(bet.getId())
                            .status(bet.getStatus())
                            .date(bet.getDate())
                            .amount(bet.getAmount())
                            .matchEvents(matchEventDtos)
                            .coefficient(productOfCoefficients)
                            .winnings(winnings)
                            .build();
                });
    }

    @Transactional
    public void makeBet(MakeBetRequest request) {
        User user = userRetrievalService.getUserFromContext();
        if (balanceService.getBalanceForUser(user).getAmount() < request.getAmount()) {
            throw new AppError(HttpStatus.BAD_REQUEST.value(), "Недостаточно денег");
        }
        Set<Integer> eventIds = request.getIds();

        Set<MatchEvent> events = matchEventRepository.findAllByIdIn(eventIds);

        Bet bet = Bet.builder()
                .matchEvents(events)
                .amount(request.getAmount())
                .date(Instant.now())
                .status(BetStatus.TBD)
                .user(user)
                .build();
        betRepository.save(bet);
        balanceService.changeUserBalance(user, -request.getAmount());
        log.info("User {} has made a bet with amount {}", user.getLogin(), request.getAmount());
    }
}
