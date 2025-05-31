package com.bebra.betting.repositories;

import com.bebra.betting.models.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author max_pri
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Page<Transaction> getTransactionByUserId(Pageable pageable, Integer userId);
}
