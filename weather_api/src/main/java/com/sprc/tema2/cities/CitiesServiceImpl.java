package com.sprc.tema2.cities;

import com.sprc.tema2.countries.CountriesService;
import com.sprc.tema2.ids.IdsService;
import com.sprc.tema2.temperatures.Temperatures;
import com.sprc.tema2.temperatures.TemperaturesRepository;
import com.sprc.tema2.temperatures.TemperaturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitiesServiceImpl implements CitiesService {

    @Autowired
    CountriesService countriesService;

    @Autowired
    TemperaturesService temperaturesService;

    @Autowired
    IdsService idsService;

    @Autowired
    CitiesRepository citiesRepository;

    @Autowired
    TemperaturesRepository temperaturesRepository;

    @Override
    public Integer addCity(Cities city) {

        // Verificare existenta tara cu idTara
        if (countriesService.getEntryById(city.getIdTara()) == null)
            return -1;

        // Verificare unicitate pereche (idTara,nume)
        if (citiesRepository.findByIdTaraAndNume(city.getIdTara(), city.getNume()) != null)
            return null;

        // Setare id si returnare
        Integer nextId = idsService.generateSequence("CITIES_SEQ");
        city.setId(nextId);
        citiesRepository.save(city);
        return nextId;
    }

    @Override
    public List<Cities> getCities() {
        return citiesRepository.findAll();
    }

    @Override
    public List<Cities> getCitiesByCountryId(Integer id) {
        return citiesRepository.findAllByIdTara(id);
    }

    @Override
    public boolean updateEntryById(Cities updatedCity) {
        Cities citiesForId = citiesRepository.findById(updatedCity.getId());

        // Verificarea daca exista orasul cu id-ul dat
        if (citiesForId == null)
            return false;

        /*
         * Verifica daca deja exista alta intrare cu (idTara,numeOras) in
         * care se modifica (daca se modifica) numele sau idTara pentru id-ul specificat
         */
        if (!citiesForId.getNume().equals(updatedCity.getNume())
                || !citiesForId.getIdTara().equals(updatedCity.getIdTara()))
            if (citiesRepository.findByIdTaraAndNume(updatedCity.getIdTara(), updatedCity.getNume()) != null)
                return false;

        citiesRepository.save(updatedCity);
        return true;
    }

    @Override
    public boolean deleteEntryById(Integer id) {
        if (citiesRepository.findById(id) == null)
            return false;

        // Stergerea in cascada a tuturor temperaturilor corespuznatoare id-ului orasului
        List<Temperatures> temperaturesByCities = temperaturesService.getTemperaturesByCityId(id);
        for ( Temperatures temperatures : temperaturesByCities)
            temperaturesService.deleteEntryById(temperatures.getId());

        citiesRepository.deleteById(id);
        return true;
    }

    @Override
    public Cities getEntryById(Integer id) {
        return citiesRepository.findById(id);
    }

}
