package com.sprc.tema2.countries;

import com.sprc.tema2.cities.Cities;
import com.sprc.tema2.cities.CitiesService;
import com.sprc.tema2.ids.IdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountriesServiceImpl implements CountriesService {

    @Autowired
    private CountriesRepository countriesRepository;

    @Autowired
    private CitiesService citiesService;

    @Autowired
    private IdsService idsService;

    @Override
    public Integer addCountry(Countries country) {

        // Verificare unicitate nume tara
        if (countriesRepository.findByNume(country.getNume())!=null)
            return null;

        Integer nextId = idsService.generateSequence("COUNTRIES_SEQ");
        country.setId(nextId);
        countriesRepository.save(country);
        return nextId;
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

        /* Verifica daca deja exista alta intrare cu numele tarii in
         * care se modifica (daca se modifica) numele pentru id-ul specificat
         */
        if(!countryForId.getNume().equals(updatedCountry.getNume()))
            if (countriesRepository.findByNume(updatedCountry.getNume())!=null)
                return false;

        countriesRepository.save(updatedCountry);
        return true;
    }

    @Override
    public boolean deleteEntryById(Integer id) {
        if(countriesRepository.findById(id)==null)
            return false;

        // Stergerea in cascada a tuturor oraselor corespuznatoare id-ului tarii
        List<Cities> citiesByCountry = citiesService.getCitiesByCountryId(id);
        for ( Cities city : citiesByCountry)
            citiesService.deleteEntryById(city.getId());

        // Stergerea tarii corespunzatoare id-ului
        countriesRepository.deleteById(id);
        return true;
    }

    @Override
    public Countries getEntryById(Integer id) {
        return countriesRepository.findById(id);
    }
}