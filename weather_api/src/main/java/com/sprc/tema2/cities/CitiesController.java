package com.sprc.tema2.cities;

import com.sprc.tema2.countries.CountriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/cities")
@CrossOrigin
public class CitiesController {

    @Autowired
    private CitiesService citiesService;

    @Autowired
    private CountriesService countriesService;

    @PostMapping
    public ResponseEntity<Map<String,Integer>> addCountry(@RequestBody Map<String, String> mapCity) {

        Map<String,Integer> mapResult = new HashMap<>();

        if(mapCity.get("idTara") == null || mapCity.get("nume")== null || mapCity.get("lat") == null || mapCity.get("lon") == null)
            return new ResponseEntity<>(mapResult, HttpStatus.BAD_REQUEST);

        // TODO verificare daca lat/lon contin litere - functie de getById

        // TODO add bad request daca nu exista tara

        Cities city = new Cities(Integer.valueOf(mapCity.get("idTara")),mapCity.get("nume"), Double.valueOf(mapCity.get("lat")),
                Double.valueOf(mapCity.get("lon")));

        Integer resultId = citiesService.addCity(city);

        if(resultId!=null)
        {
            mapResult.put("id",resultId);
            return new ResponseEntity<>(mapResult, HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>(mapResult, HttpStatus.CONFLICT);
    }

    @GetMapping
    public ResponseEntity<List<Cities>> getCities(){
        return new ResponseEntity<>(citiesService.getCities(),HttpStatus.OK);
    }
}
