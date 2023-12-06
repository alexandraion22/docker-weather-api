package com.sprc.tema2.countries;

import org.apache.coyote.Response;
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

        // TODO verificare daca lat/lon contin litere

        Countries country = new Countries(mapCountry.get("nume"), Double.valueOf(mapCountry.get("lat")),
                Double.valueOf(mapCountry.get("lon")));

        if (countriesService.addCountry(country))
            return new ResponseEntity<>("Country added successfully.", HttpStatus.CREATED);
        else
            return new ResponseEntity<>("Country already exists in the database.", HttpStatus.CONFLICT);
    }

    @GetMapping
    public ResponseEntity<List<Countries>> getCountries(){
        return new ResponseEntity<>(countriesService.getCountries(),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateById(@PathVariable("id") Integer id, @RequestBody Map<String, String> mapCountry){

        if(mapCountry.get("id") == null || mapCountry.get("nume")== null || mapCountry.get("lat") == null || mapCountry.get("lon") == null)
            return new ResponseEntity<>("Missing Request Parameters", HttpStatus.BAD_REQUEST);

        // TODO verificare daca id,lat,lon sunt valide

        if(id!=Integer.valueOf(mapCountry.get("id")))
            return new ResponseEntity<>("Path variable not matching the body", HttpStatus.BAD_REQUEST);

        Countries updateCountry = new Countries(mapCountry.get("nume"), Double.valueOf(mapCountry.get("lat")),Double.valueOf(mapCountry.get("lon")));
        updateCountry.setId(id);

        if(countriesService.updateEntryById(updateCountry))
            return new ResponseEntity<>("Entry updated successfully.", HttpStatus.OK);
        else
            return new ResponseEntity<>("Id not found or country with the name provided already exists", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Integer id){

        if(countriesService.deleteEntryById(id))
            return new ResponseEntity<>("Entry deleted successfully", HttpStatus.OK);
        else
            return new ResponseEntity<>("Id not found", HttpStatus.NOT_FOUND);
    }
}
