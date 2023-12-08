package com.sprc.tema2.temperatures;

import com.sprc.tema2.cities.CitiesService;
import com.sprc.tema2.ids.IdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Temperatures> getTemperatures() {
        return temperaturesRepository.findAll();
    }
}
