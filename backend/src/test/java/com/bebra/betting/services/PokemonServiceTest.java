package com.bebra.betting.services;

import com.bebra.betting.models.entity.Country;
import com.bebra.betting.models.entity.Pokemon;
import com.bebra.betting.repositories.CountryRepository;
import com.bebra.betting.repositories.PokemonRepository;
import org.bebra.dto.PokemonDto;
import org.bebra.dto.requests.CreatePokemonRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PokemonServiceTest {

    @Mock
    private PokemonRepository pokemonRepository;

    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private PokemonService pokemonService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getPokemon_returnsPokemonDto_whenPokemonExists() {
        String name = "Pikachu";
        PokemonDto expectedPokemonDto = createPokemonDto();
        when(pokemonRepository.findPokemonByName(name)).thenReturn(expectedPokemonDto);

        PokemonDto actualPokemonDto = pokemonService.getPokemon(name);

        assertEquals(expectedPokemonDto, actualPokemonDto);
        verify(pokemonRepository, times(1)).findPokemonByName(name);
    }

    @Test
    void getPokemon_throwsException_whenPokemonDoesNotExist() {
        String name = "Unknown";
        when(pokemonRepository.findPokemonByName(name)).thenReturn(null);

        assertEquals(null, pokemonService.getPokemon(name));
        verify(pokemonRepository, times(1)).findPokemonByName(name);
    }

    @Test
    void getAll_returnsListOfPokemonDto() {
        List<PokemonDto> expectedPokemonDtos = List.of(createPokemonDto(), createPokemonDto());
        when(pokemonRepository.getAll()).thenReturn(expectedPokemonDtos);

        List<PokemonDto> actualPokemonDtos = pokemonService.getAll();

        assertEquals(expectedPokemonDtos, actualPokemonDtos);
        verify(pokemonRepository, times(1)).getAll();
    }

    @Test
    void create_savesPokemon_whenRequestIsValid() {
        CreatePokemonRequest request = new CreatePokemonRequest();
        request.setName("Pikachu");
        request.setDamage(50);
        request.setHealth(100);
        request.setDefense(30);
        request.setCountryId(1);

        Country country = new Country();
        when(countryRepository.findById(request.getCountryId())).thenReturn(Optional.of(country));

        pokemonService.create(request);

        verify(pokemonRepository, times(1)).save(any(Pokemon.class));
    }

    @Test
    void create_throwsException_whenCountryDoesNotExist() {
        CreatePokemonRequest request = new CreatePokemonRequest();
        request.setCountryId(1);
        when(countryRepository.findById(request.getCountryId())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> pokemonService.create(request));
        verify(pokemonRepository, never()).save(any(Pokemon.class));
    }

    private PokemonDto createPokemonDto() {
        PokemonDto pokemonDto = new PokemonDto(1, "tes", 1, 1, 1, "rus");
        pokemonDto.setName("Pikachu");
        pokemonDto.setDamage(50);
        pokemonDto.setHealth(100);
        pokemonDto.setDefense(30);
        return pokemonDto;
    }
}