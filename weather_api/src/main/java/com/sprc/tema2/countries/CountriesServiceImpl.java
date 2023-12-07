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
    public Integer addCountry(Countries country) {

        // Verificare unicitate nume tara
        if (countriesRepository.findByNume(country.getNume())!=null)
            return null;
        else
        {
            Integer nextId = idsService.generateSequence("COUNTRIES_SEQ");
            country.setId(nextId);
            countriesRepository.save(country);
            return nextId;
        }
    }

    @Override
    public List<Countries> getCountries() {
        return countriesRepository.findAll();
    }

    @Override
    public boolean updateEntryById(Countries updatedCountry) {

        Countries countryForId = countriesRepository.findById(updatedCountry.getId());

        if(countryForId==null)
            return false;

        // TODO Poate sa elaborez mai mult totusi
        if(!countryForId.getNume().equals(updatedCountry.getNume()))
            if (countriesRepository.findByNume(updatedCountry.getNume())!=null)
                return false;

        countriesRepository.save(updatedCountry);
        return true;
    }

    @Override
    public boolean deleteEntryById(Integer id) {
        Countries countryForId = countriesRepository.findById(id);

        if(countryForId!=null)
        {
            countriesRepository.deleteById(id);
            return true;
        }
        else
            return false;
    }
}