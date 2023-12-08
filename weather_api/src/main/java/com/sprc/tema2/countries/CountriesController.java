package com.sprc.tema2.countries;

import com.sprc.utils.UtilsHw;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
    public ResponseEntity<Map<String, Integer>> addCountry(@RequestBody Map<String, String> map) {

        if (UtilsHw.hasNullParameters(map, Arrays.asList("nume", "lat", "lon")))
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        Countries country = new Countries(map.get("nume"), Double.valueOf(map.get("lat")),
                Double.valueOf(map.get("lon")));

        Integer resultId = countriesService.addCountry(country);

        // Return id al noii intrari in tabela (daca s-a creat)
        if (resultId != null)
            return new ResponseEntity<>(new HashMap<>() {
                {
                    put("id", resultId);
                }
            }, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
    }

    @GetMapping
    public ResponseEntity<List<Countries>> getCountries() {
        return new ResponseEntity<>(countriesService.getCountries(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateById(@PathVariable("id") Integer id, @RequestBody Map<String, String> map) {

        if (UtilsHw.hasNullParameters(map, Arrays.asList("id", "nume", "lat", "lon")))
            return new ResponseEntity<>("Missing parameters in request body.", HttpStatus.BAD_REQUEST);

        // Verificarea id-ului din PathVariable sa corespunda cu id-ul din body
        if (id != Integer.valueOf(map.get("id")))
            return new ResponseEntity<>("Path variable id and the id in the request body do not match.",
                    HttpStatus.BAD_REQUEST);

        // Creare obiect cu parmetrii din request body
        Countries updateCountry = new Countries(map.get("nume"), Double.valueOf(map.get("lat")),
                Double.valueOf(map.get("lon")));
        updateCountry.setId(id);

        if (countriesService.updateEntryById(updateCountry))
            return new ResponseEntity<>("Country updated successfully.", HttpStatus.OK);
        else
            return new ResponseEntity<>("Country not found.", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Integer id) {

        if (countriesService.deleteEntryById(id))
            return new ResponseEntity<>("Country and adjacent cities and temperatures were deleted successfully.",
                    HttpStatus.OK);
        else
            return new ResponseEntity<>("Country not found.", HttpStatus.NOT_FOUND);
    }
}
