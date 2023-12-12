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

        try {
            Countries country = new Countries(map.get("nume"), Double.parseDouble(map.get("lat")),
                    Double.parseDouble(map.get("lon")));

            Integer resultId = countriesService.addCountry(country);
            return UtilsHw.mapId(resultId);
        } catch (NumberFormatException nfe) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Countries>> getCountries() {
        return new ResponseEntity<>(countriesService.getCountries(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateById(@PathVariable("id") Integer id, @RequestBody Map<String, String> map) {

        if (UtilsHw.hasNullParameters(map, Arrays.asList("id", "nume", "lat", "lon")))
            return new ResponseEntity<>("Missing parameters in request body.", HttpStatus.BAD_REQUEST);

        try {
            // Verificarea id-ului din PathVariable sa corespunda cu id-ul din body
            if (id != Integer.parseInt(map.get("id")))
                return new ResponseEntity<>("Path variable id and the id in the request body do not match.",
                        HttpStatus.BAD_REQUEST);

            // Creare obiect cu parametrii din request body
            Countries updateCountry = new Countries(map.get("nume"), Double.parseDouble(map.get("lat")),
                    Double.parseDouble(map.get("lon")));
            updateCountry.setId(id);

            Integer result = countriesService.updateEntryById(updateCountry);
            if (result == 1)
                return new ResponseEntity<>("Country updated successfully.", HttpStatus.OK);
            else {
                if (result == 0)
                    return new ResponseEntity<>("Country not found.", HttpStatus.NOT_FOUND);
                else
                    return new ResponseEntity<>("Country in update, pair already exists.", HttpStatus.CONFLICT);
            }
        } catch (NumberFormatException nfe) {
            return new ResponseEntity<>("Wrong format of parameters.", HttpStatus.BAD_REQUEST);
        }
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
