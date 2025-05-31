package com.bebra.betting.repositories;

import com.bebra.betting.models.entity.Pokemon;
import org.bebra.dto.PokemonDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author max_pri
 */
@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {

    @Query("select new org.bebra.dto.PokemonDto(p.id, p.name, p.health, p.damage, p.defense, p.country.name) FROM Pokemon p where p.name=:name")
    PokemonDto findPokemonByName(@Param("name") String name);

    @Query("select new org.bebra.dto.PokemonDto(p.id, p.name, p.health, p.damage, p.defense, p.country.name) FROM Pokemon p")
    List<PokemonDto> getAll();
}
