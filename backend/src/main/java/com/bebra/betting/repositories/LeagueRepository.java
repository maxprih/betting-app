package com.bebra.betting.repositories;

import com.bebra.betting.models.entity.League;
import org.bebra.dto.LeagueDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author max_pri
 */
@Repository
public interface LeagueRepository extends JpaRepository<League, Integer> {

    @Query("select new org.bebra.dto.LeagueDto(l.id, l.name, l.startDate, l.endDate) from League l")
    List<LeagueDto> getAll();
}
