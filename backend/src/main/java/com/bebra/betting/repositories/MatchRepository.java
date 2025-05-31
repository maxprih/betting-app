package com.bebra.betting.repositories;

import com.bebra.betting.models.entity.Match;
import org.bebra.dto.MatchDto;
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
public interface MatchRepository extends JpaRepository<Match, Integer> {
    Page<Match> findAll(Pageable pageable);

    @Query("SELECT new org.bebra.dto.MatchDto(m.id, m.firstPlayer.name, m.secondPlayer.name, m.date, m.league.name, m.country.name) " +
            "FROM Match m WHERE m.id = :id")
    MatchDto findMatchById(@Param("id") Integer id);


}
