package org.bebra.balanceservice.repositories;

import org.bebra.balanceservice.models.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author max_pri
 */
@Repository
public interface BalanceRepository extends JpaRepository<Balance, Integer> {
    Balance findBalanceByUserId(Integer userId);
}
