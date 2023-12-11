package com.sprc.tema2.cities;

import com.sprc.utils.UtilsHw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/cities")
@CrossOrigin
public class CitiesController {

    @Autowired
    private CitiesService citiesService;

    @PostMapping
    public ResponseEntity<Map<String, Integer>> addCountry(@RequestBody Map<String, String> map) {

        if (UtilsHw.hasNullParameters(map, Arrays.asList("idTara", "nume", "lat", "lon")))
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        try {
            Cities city = new Cities(Integer.valueOf(map.get("idTara")), map.get("nume"), Double.valueOf(map.get("lat")),
                    Double.valueOf(map.get("lon")));

            Integer resultId = citiesService.addCity(city);
            return UtilsHw.mapId(resultId);
        }
        catch(NumberFormatException nfe) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Cities>> getCities() {
        return new ResponseEntity<>(citiesService.getCities(), HttpStatus.OK);
    }

    @GetMapping("/country/{id}")
    public ResponseEntity<List<Cities>> getCitiesByCountry(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(citiesService.getCitiesByCountryId(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateById(@PathVariable("id") Integer id, @RequestBody Map<String, String> map) {

        if (UtilsHw.hasNullParameters(map, Arrays.asList("id", "idTara", "nume", "lat", "lon")))
            return new ResponseEntity<>("Missing parameters in request body.", HttpStatus.BAD_REQUEST);

        try {
            // Verificarea id-ului din PathVariable sa corespunda cu id-ul din body
            if (id != Integer.parseInt(map.get("id")))
                return new ResponseEntity<>("Path variable id and the id in the request body do not match.",
                        HttpStatus.BAD_REQUEST);

            // Creare obiect cu parametrii din request body
            Cities updatedCity = new Cities(Integer.parseInt(map.get("idTara")), map.get("nume"),
                    Double.parseDouble(map.get("lat")), Double.parseDouble(map.get("lon")));
            updatedCity.setId(id);

            if (citiesService.updateEntryById(updatedCity))
                return new ResponseEntity<>("City updated successfully.", HttpStatus.OK);
            else
                return new ResponseEntity<>("City not found.", HttpStatus.NOT_FOUND);
        }
        catch(NumberFormatException nfe){
            return new ResponseEntity<>("Wrong format of parameters.",HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Integer id) {

        if (citiesService.deleteEntryById(id))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
