package com.bebra.betting.repositories;

import com.bebra.betting.models.entity.Bet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author max_pri
 */
@Repository
public interface BetRepository extends JpaRepository<Bet, Integer> {
    Page<Bet> findAllByUserId(Pageable pageable, Integer userId);

    @Query(nativeQuery = true, value = "select * from calculate_bet_winnings(:betId)")
    Double getBetWinning(@Param("betId") Integer betId);
}
