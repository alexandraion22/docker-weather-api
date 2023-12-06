package com.sprc.tema2.countries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountriesServiceImpl implements CountriesService {

    @Autowired
    private CountriesRepository countriesRepository;

    @Override
    public boolean saveCountry(Countries country) {
        if (countriesRepository.existsById(country.getNume()))
            return false;
        else
            countriesRepository.save(country);
        return true;
    }
}