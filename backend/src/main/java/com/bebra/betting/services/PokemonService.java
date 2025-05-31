package com.bebra.betting.services;

import com.bebra.betting.models.entity.Country;
import com.bebra.betting.models.entity.Pokemon;
import com.bebra.betting.repositories.CountryRepository;
import com.bebra.betting.repositories.PokemonRepository;
import org.bebra.dto.PokemonDto;
import org.bebra.dto.requests.CreatePokemonRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author max_pri
 */
@Service
public class PokemonService {
    private final PokemonRepository pokemonRepository;
    private final CountryRepository countryRepository;

    @Autowired
    public PokemonService(PokemonRepository pokemonRepository, CountryRepository countryRepository) {
        this.pokemonRepository = pokemonRepository;
        this.countryRepository = countryRepository;
    }

    public PokemonDto getPokemon(String name) {
        return pokemonRepository.findPokemonByName(name);
    }

    public List<PokemonDto> getAll() {
        return pokemonRepository.getAll();
    }

    public void create(CreatePokemonRequest request) {
        Pokemon pokemon = new Pokemon();
        Country country = countryRepository.findById(request.getCountryId()).get();
        pokemon.setName(request.getName());
        pokemon.setDamage(request.getDamage());
        pokemon.setHealth(request.getHealth());
        pokemon.setDefense(request.getDefense());
        pokemon.setCountry(country);

        pokemonRepository.save(pokemon);
    }
}
