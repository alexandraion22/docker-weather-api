package com.sprc.tema2.countries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/countries")
@CrossOrigin
public class CountriesController {

    @Autowired
    private CountriesService countriesService;

    @PostMapping("")
    public ResponseEntity<String> addCountry(@RequestBody Map<String, String> mapCountry) {

        Countries country = new Countries(mapCountry.get("nume"), Double.valueOf(mapCountry.get("lat")),
                Double.valueOf(mapCountry.get("lon")));

        if (countriesService.saveCountry(country))
            return new ResponseEntity<>("Country added successfully.", HttpStatus.OK);
        else
            return new ResponseEntity<>("Country already exists in the database.", HttpStatus.CONFLICT);
    }
}
