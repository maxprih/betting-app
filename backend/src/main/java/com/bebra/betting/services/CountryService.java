package com.bebra.betting.services;

import com.bebra.betting.models.entity.Country;
import com.bebra.betting.repositories.CountryRepository;
import org.bebra.dto.requests.CreateCountryRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author max_pri
 */
@Service
public class CountryService {
    private final CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public void create(CreateCountryRequest request) {
        Country country = new Country();
        country.setName(request.getName());

        countryRepository.save(country);
    }
}
