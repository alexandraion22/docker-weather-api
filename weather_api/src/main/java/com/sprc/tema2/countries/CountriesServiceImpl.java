package com.sprc.tema2.countries;

import com.sprc.tema2.ids.IdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountriesServiceImpl implements CountriesService {

    @Autowired
    private CountriesRepository countriesRepository;

    @Autowired
    private IdsService idsService;

    @Override
    public boolean addCountry(Countries country) {
        if (countriesRepository.findByNume(country.getNume())!=null)
            return false;
        else
        {
            Integer nextId = idsService.getNextId("countries");
            country.setId(nextId);
            countriesRepository.save(country);
        }
        return true;
    }

    @Override
    public List<Countries> getCountries() {
        return countriesRepository.findAll();
    }
}