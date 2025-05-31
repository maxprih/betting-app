package com.bebra.betting.controllers;

import com.bebra.betting.repositories.CountryRepository;
import com.bebra.betting.services.CountryService;
import org.bebra.dto.CountryDto;
import org.bebra.dto.requests.CreateCountryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author max_pri
 */
@RestController
@RequestMapping("api/v1/country")
public class CountryController {
    private final CountryRepository countryRepository;
    private final CountryService countryService;

    @Autowired
    public CountryController(CountryRepository countryRepository, CountryService countryService) {
        this.countryRepository = countryRepository;
        this.countryService = countryService;
    }

    @GetMapping()
    public ResponseEntity<List<CountryDto>> getAll() {
        return ResponseEntity.ok(countryRepository.getAllCountries());
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody CreateCountryRequest request) {
        countryService.create(request);
        return ResponseEntity.ok().build();
    }
}
