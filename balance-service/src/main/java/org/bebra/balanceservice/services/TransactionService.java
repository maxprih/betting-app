package org.bebra.balanceservice.services;

import lombok.RequiredArgsConstructor;
import org.bebra.balanceservice.web.MainClient;
import org.springframework.stereotype.Service;

/**
 * @author max_pri
 */
@Service
@RequiredArgsConstructor
public class TransactionService {
    private final MainClient mainClient;

    public void createNewTransaction(Integer userId, Integer amount) {
        mainClient.createNewTransaction(userId, amount);
    }
}
