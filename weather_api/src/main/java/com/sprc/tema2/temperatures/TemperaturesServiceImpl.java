package com.sprc.tema2.temperatures;

import com.sprc.tema2.cities.CitiesService;
import com.sprc.tema2.ids.IdsService;
import com.sprc.utils.UtilsHw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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

        List<Temperatures> temperaturesFilteredByLatAndLon;

        if (lat != null && lon != null)
            temperaturesFilteredByLatAndLon = temperatures.stream()
                    .filter(t -> (citiesService.getEntryById(t.getIdOras()).getLat() == lat
                            && citiesService.getEntryById(t.getIdOras()).getLon() == lon))
                    .collect(Collectors.toList());
        else {
            if (lat != null)
                temperaturesFilteredByLatAndLon = temperatures.stream()
                        .filter(t -> citiesService.getEntryById(t.getIdOras()).getLat() == lat)
                        .collect(Collectors.toList());
            else {
                if (lon != null)
                    temperaturesFilteredByLatAndLon = temperatures.stream()
                            .filter(t -> citiesService.getEntryById(t.getIdOras()).getLon() == lon)
                            .collect(Collectors.toList());
                else
                    temperaturesFilteredByLatAndLon = temperatures;
            }
        }

        return UtilsHw.filterListByFromAndUntil(temperaturesFilteredByLatAndLon, from, until);
    }

    @Override
    public List<Temperatures> getCityTemperatures(Integer cityId, Date from, Date until) {
        return UtilsHw.filterListByFromAndUntil(temperaturesRepository.findAllByIdOras(cityId), from, until);
    }
}
