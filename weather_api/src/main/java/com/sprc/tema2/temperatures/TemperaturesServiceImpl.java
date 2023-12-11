package com.sprc.tema2.temperatures;

import com.sprc.tema2.cities.Cities;
import com.sprc.tema2.cities.CitiesService;
import com.sprc.tema2.countries.Countries;
import com.sprc.tema2.ids.IdsService;
import com.sprc.utils.UtilsHw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TemperaturesServiceImpl implements TemperaturesService {

    @Autowired
    CitiesService citiesService;

    @Autowired
    TemperaturesRepository temperaturesRepository;

    @Autowired
    IdsService idsService;

    @Override
    public Integer addTemperature(Temperatures temperature) {
        // Verificare existenta oras cu idOras
        if (citiesService.getEntryById(temperature.getIdOras()) == null)
            return -1;

        // Verificare unicitate pereche (idOras, timestamp)
        if (temperaturesRepository.findByIdOrasAndTimestamp(temperature.getIdOras(),
                temperature.getTimestamp()) != null)
            return null;

        // Setare id si returnare
        Integer nextId = idsService.generateSequence("TEMPERATURES_SEQ");
        temperature.setId(nextId);
        temperaturesRepository.save(temperature);
        return nextId;
    }

    @Override
    public List<Temperatures> getTemperatures(Double lat, Double lon, Date from, Date until) {
        List<Temperatures> temperatures = temperaturesRepository.findAll();

        List<Temperatures> temperaturesFilteredByLatAndLon = temperatures;

        if (until != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(until);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            until = calendar.getTime();
        }

        if (lat != null && lon != null)
            temperaturesFilteredByLatAndLon = temperatures.stream()
                    .filter(t -> (citiesService.getEntryById(t.getIdOras()).getLat().equals(lat)
                            && citiesService.getEntryById(t.getIdOras()).getLon().equals(lon)))
                    .collect(Collectors.toList());
        else {
            if (lat != null)
                temperaturesFilteredByLatAndLon = temperatures.stream()
                        .filter(t -> citiesService.getEntryById(t.getIdOras()).getLat().equals(lat))
                        .collect(Collectors.toList());
            else if (lon != null)
                temperaturesFilteredByLatAndLon = temperatures.stream()
                        .filter(t -> citiesService.getEntryById(t.getIdOras()).getLon().equals(lon))
                        .collect(Collectors.toList());
        }

        return UtilsHw.filterListByFromAndUntil(temperaturesFilteredByLatAndLon, from, until);
    }

    @Override
    public List<Temperatures> getCityTemperatures(Integer cityId, Date from, Date until) {
        if (until != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(until);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            until = calendar.getTime();
        }

        return UtilsHw.filterListByFromAndUntil(temperaturesRepository.findAllByIdOras(cityId), from, until);
    }

    @Override
    public List<Temperatures> getCountryTemperatures(Integer countryId, Date from, Date until) {
        if (until != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(until);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            until = calendar.getTime();
        }
        List<Cities> citiesInCountry = citiesService.getCitiesByCountryId(countryId);
        List<Temperatures> temperaturesInCountry = new ArrayList<>();
        for (Cities city : citiesInCountry)
            temperaturesInCountry.addAll(temperaturesRepository.findAllByIdOras(city.getId()));
        return UtilsHw.filterListByFromAndUntil(temperaturesInCountry, from, until);
    }

    @Override
    public List<Temperatures> getTemperaturesByCityId(Integer cityId) {
        return temperaturesRepository.findAllByIdOras(cityId);
    }

    @Override
    public boolean updateEntryById(Temperatures updateTemperature) {
        Temperatures temperatureForId = temperaturesRepository.findById(updateTemperature.getId());

        if(temperatureForId==null)
            return false;


        /*
         * Verifica daca deja exista alta intrare cu (idTara,numeOras) in
         * care se modifica (daca se modifica) numele sau idTara pentru id-ul specificat
         */
        updateTemperature.setTimestamp(temperatureForId.getTimestamp());
        temperaturesRepository.save(updateTemperature);
        return true;
    }

    @Override
    public boolean deleteEntryById(Integer id) {
        if (temperaturesRepository.findById(id) == null)
            return false;
        temperaturesRepository.deleteById(id);
        return true;
    }
}
