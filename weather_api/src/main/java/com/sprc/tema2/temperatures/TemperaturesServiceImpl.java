package com.sprc.tema2.temperatures;

import com.sprc.tema2.cities.CitiesService;
import com.sprc.tema2.ids.IdsService;
import com.sprc.utils.UtilsHw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
        if (temperaturesRepository.findByIdOrasAndTimestamp(temperature.getIdOras(), temperature.getTimestamp()) != null)
            return null;

        // Setare id si returnare
        Integer nextId = idsService.generateSequence("TEMPERATURES_SEQ");
        temperature.setId(nextId);
        temperaturesRepository.save(temperature);
        return nextId;
    }

    @Override
    public List<Temperatures> getTemperatures(Double lat, Double lon, Date from, Date until) {
        List<Temperatures> temperaturesByLatAndLon;

        if(lat == null)
        {
            if (lon == null)
                temperaturesByLatAndLon = temperaturesRepository.findAll();
            else
                temperaturesByLatAndLon = temperaturesRepository.findAllByLon(lon);
        }
        else {
            if (lon == null)
                temperaturesByLatAndLon = temperaturesRepository.findAllByLat(lat);
            else
                temperaturesByLatAndLon = temperaturesRepository.findAllByLatAndLon(lat,lon);
        }

        if(from == null && until == null)
            return temperaturesByLatAndLon;

        return UtilsHw.filterListByFromAndUntil(temperaturesByLatAndLon,from,until);
    }

    @Override
    public List<Temperatures> getCityTemperatures(Integer cityId, Date from, Date until) {
        return UtilsHw.filterListByFromAndUntil(temperaturesRepository.findAllByIdOras(cityId), from, until);
    }
}
