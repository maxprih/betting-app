package com.bebra.betting.repositories;

import com.bebra.betting.models.entity.Country;
import org.bebra.dto.CountryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author max_pri
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

    @Query("select new org.bebra.dto.CountryDto(c.id, c.name) from Country c")
    List<CountryDto> getAllCountries();

    Optional<Country> findById(Integer id);
}
