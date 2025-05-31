package org.bebra.balanceservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bebra.balanceservice.models.Balance;
import org.bebra.balanceservice.repositories.BalanceRepository;
import org.bebra.dto.BalanceDto;
import org.springframework.stereotype.Service;

/**
 * @author max_pri
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BalanceService {
    private final BalanceRepository balanceRepository;
    private final TransactionService transactionService;

    public Balance createBalanceForUser(Integer userId) {
        Balance balance = new Balance();
        balance.setUserId(userId);

        return balanceRepository.save(balance);
    }

    public Balance changeUserBalance(Integer userId, Integer amount) {
        Balance balance = balanceRepository.findBalanceByUserId(userId);
        transactionService.createNewTransaction(userId, amount);
        balance.setAmount(balance.getAmount() + amount);
        log.info("User {} has changed balance on {}", userId, amount);
        return balanceRepository.save(balance);
    }

    public BalanceDto getBalanceForUser(Integer userId) {
        Balance balance = balanceRepository.findBalanceByUserId(userId);
        return new BalanceDto(balance.getId(), balance.getUserId(), balance.getAmount());
    }
}
