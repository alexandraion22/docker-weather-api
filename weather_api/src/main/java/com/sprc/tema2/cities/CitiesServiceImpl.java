package com.sprc.tema2.cities;

import com.sprc.tema2.ids.IdsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitiesServiceImpl implements CitiesService{

    @Autowired
    CitiesRepository citiesRepository;

    @Autowired
    IdsService idsService;

    @Override
    public Integer addCity(Cities city) {
        // Verificare unicitate nume tara
        if (citiesRepository.findByIdTaraAndNume(city.getIdTara(),city.getNume())!=null)
            return null;
        else
        {
            Integer nextId = idsService.generateSequence("COUNTRIES_SEQ");
            city.setId(nextId);
            citiesRepository.save(city);
            return nextId;
        }
    }

    @Override
    public List<Cities> getCities() {
        return citiesRepository.findAll();
    }
}
