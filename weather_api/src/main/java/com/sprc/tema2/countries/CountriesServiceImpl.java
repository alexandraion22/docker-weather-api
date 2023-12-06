package com.sprc.tema2.countries;

import com.sprc.tema2.ids.IdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountriesServiceImpl implements CountriesService {

    @Autowired
    private CountriesRepository countriesRepository;

    @Autowired
    private IdsService idsService;

    @Override
    public boolean addCountry(Countries country) {

        // Verificare unicitate nume tara
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

    @Override
    public boolean updateEntryById(Integer id, Countries country) {

        Optional<Countries> countryForId = countriesRepository.findById(String.valueOf(id));

        if(countryForId.isEmpty())
            return false;

        // TODO Poate sa elaborez mai mult totusi
        if(countryForId.get().getNume() != country.getNume())
            if (countriesRepository.findByNume(country.getNume())!=null)
                return false;
        country.setId(countriesRepository.findById(String.valueOf(id)).get().getId());
        countriesRepository.save(country);
        return true;
    }

    @Override
    public boolean deleteEntryById(Integer id) {
        Optional<Countries> countryForId = countriesRepository.findById(String.valueOf(id));

        if(countryForId.isPresent())
        {
            countriesRepository.deleteById(String.valueOf(id));
            return true;
        }
        else
            return false;
    }
}