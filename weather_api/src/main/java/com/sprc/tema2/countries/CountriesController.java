package com.sprc.tema2.countries;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/countries")
@CrossOrigin
public class CountriesController {

    @Autowired
    private CountriesService countriesService;

    @PostMapping
    public ResponseEntity<Map<String,Integer>> addCountry(@RequestBody Map<String, String> mapCountry) {

        Map<String,Integer> mapResult = new HashMap<>();

        if(mapCountry.get("nume")== null || mapCountry.get("lat") == null || mapCountry.get("lon") == null)
            return new ResponseEntity<>(mapResult, HttpStatus.BAD_REQUEST);

        // TODO verificare daca lat/lon contin litere

        Countries country = new Countries(mapCountry.get("nume"), Double.valueOf(mapCountry.get("lat")),
                Double.valueOf(mapCountry.get("lon")));

       Integer resultId = countriesService.addCountry(country);

       if(resultId!=null)
       {
           mapResult.put("id",resultId);
           return new ResponseEntity<>(mapResult, HttpStatus.CREATED);
       }
       else
           return new ResponseEntity<>(mapResult, HttpStatus.CONFLICT);
    }

    @GetMapping
    public ResponseEntity<List<Countries>> getCountries(){
        return new ResponseEntity<>(countriesService.getCountries(),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateById(@PathVariable("id") Integer id, @RequestBody Map<String, String> mapCountry){

        if(mapCountry.get("id") == null || mapCountry.get("nume")== null || mapCountry.get("lat") == null || mapCountry.get("lon") == null)
            return new ResponseEntity<>("Missing Request Parameters", HttpStatus.BAD_REQUEST);

        // TODO verificare daca id,lat,lon sunt valide

        if(id!=Integer.valueOf(mapCountry.get("id")))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Countries updateCountry = new Countries(mapCountry.get("nume"), Double.valueOf(mapCountry.get("lat")),Double.valueOf(mapCountry.get("lon")));
        updateCountry.setId(id);

        if(countriesService.updateEntryById(updateCountry))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Integer id){

        if(countriesService.deleteEntryById(id))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
