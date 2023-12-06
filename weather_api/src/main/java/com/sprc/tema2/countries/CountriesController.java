package com.sprc.tema2.countries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/countries")
@CrossOrigin
public class CountriesController {

    @Autowired
    private CountriesService countriesService;

    @PostMapping
    public ResponseEntity<String> addCountry(@RequestBody Map<String, String> mapCountry) {

        if(mapCountry.get("nume")== null || mapCountry.get("lat") == null || mapCountry.get("lon") == null)
            return new ResponseEntity<>("Missing Request Parameters", HttpStatus.BAD_REQUEST);

        Countries country = new Countries(mapCountry.get("nume"), Double.valueOf(mapCountry.get("lat")),
                Double.valueOf(mapCountry.get("lon")));

        if (countriesService.addCountry(country))
            return new ResponseEntity<>("Country added successfully.", HttpStatus.OK);
        else
            return new ResponseEntity<>("Country already exists in the database.", HttpStatus.CONFLICT);
    }

    @GetMapping
    public ResponseEntity<List<Countries>> getCountries(){
        return new ResponseEntity<>(countriesService.getCountries(),HttpStatus.OK);
    }
}
