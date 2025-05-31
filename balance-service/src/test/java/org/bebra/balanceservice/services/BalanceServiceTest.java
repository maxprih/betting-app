package org.bebra.balanceservice.services;

import org.bebra.balanceservice.models.Balance;
import org.bebra.balanceservice.repositories.BalanceRepository;
import org.bebra.dto.BalanceDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BalanceServiceTest {

    @Mock
    private BalanceRepository balanceRepository;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private BalanceService balanceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBalanceForUser_createsAndSavesBalance() {
        Integer userId = 1;
        Balance balance = new Balance();
        balance.setUserId(userId);
        when(balanceRepository.save(any(Balance.class))).thenReturn(balance);

        Balance createdBalance = balanceService.createBalanceForUser(userId);

        assertEquals(userId, createdBalance.getUserId());
        verify(balanceRepository, times(1)).save(any(Balance.class));
    }

    @Test
    void changeUserBalance_updatesBalanceAndCreatesTransaction() {
        Integer userId = 1;
        Integer amount = 100;
        Balance balance = new Balance();
        balance.setUserId(userId);
        balance.setAmount(200);
        when(balanceRepository.findBalanceByUserId(userId)).thenReturn(balance);
        when(balanceRepository.save(any(Balance.class))).thenReturn(balance);

        Balance updatedBalance = balanceService.changeUserBalance(userId, amount);

        assertEquals(300, updatedBalance.getAmount());
        verify(transactionService, times(1)).createNewTransaction(userId, amount);
        verify(balanceRepository, times(1)).save(balance);
    }

    @Test
    void changeUserBalance_throwsException_whenBalanceNotFound() {
        Integer userId = 1;
        Integer amount = 100;
        when(balanceRepository.findBalanceByUserId(userId)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> balanceService.changeUserBalance(userId, amount));
        verify(balanceRepository, never()).save(any(Balance.class));
    }

    @Test
    void getBalanceForUser_returnsBalanceDto() {
        Integer userId = 1;
        Balance balance = new Balance();
        balance.setId(1);
        balance.setUserId(userId);
        balance.setAmount(200);
        when(balanceRepository.findBalanceByUserId(userId)).thenReturn(balance);

        BalanceDto balanceDto = balanceService.getBalanceForUser(userId);

        assertEquals(1, balanceDto.getId());
        assertEquals(userId, balanceDto.getUserId());
        assertEquals(200, balanceDto.getAmount());
    }

    @Test
    void getBalanceForUser_throwsException_whenBalanceNotFound() {
        Integer userId = 1;
        when(balanceRepository.findBalanceByUserId(userId)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> balanceService.getBalanceForUser(userId));
    }
}