package com.bebra.betting.services;

import com.bebra.betting.exceptions.NoFreeMoneyException;
import com.bebra.betting.models.entity.User;
import com.bebra.betting.web.BalanceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bebra.dto.BalanceDto;
import org.bebra.dto.requests.WithdrawRequest;
import org.springframework.stereotype.Service;

/**
 * @author max_pri
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BalanceService {
    private final BalanceClient balanceClient;
    private final UserRetrievalService userRetrievalService;

    public BalanceDto getBalanceForUser(User user) {
        return balanceClient.getBalance(user.getId());
    }

    public BalanceDto createBalanceForUser(User user) {
        return balanceClient.createBalance(user.getId());
    }

    public BalanceDto changeUserBalance(User user, Integer amount) {
        return balanceClient.changeBalance(user.getId(), amount);
    }

    public void addFreeMoney() {
        User user = userRetrievalService.getUserFromContext();
        if (balanceClient.getBalance(user.getId()).getAmount() < 500) {
            log.info("User {} has received 50 free money", user.getLogin());
            changeUserBalance(user, 50);
        } else {
            throw new NoFreeMoneyException();
        }
    }

    public void withdraw(WithdrawRequest request) {
        User user = userRetrievalService.getUserFromContext();
        changeUserBalance(user, -request.getAmount());

        log.info("User {} has withdrawn {}", user.getLogin(), request.getAmount());
    }
}
