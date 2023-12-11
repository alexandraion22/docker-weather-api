package com.sprc.tema2.temperatures;

import com.sprc.tema2.cities.Cities;
import com.sprc.utils.UtilsHw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/temperatures")
@CrossOrigin
public class TemperaturesController {
    @Autowired
    TemperaturesService temperaturesService;

    @PostMapping
    ResponseEntity<Map<String, Integer>> addTemperature(@RequestBody Map<String, String> map) {

        if (UtilsHw.hasNullParameters(map, Arrays.asList("idOras", "valoare")))
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        try {
            Temperatures temperatures = new Temperatures(Integer.parseInt(map.get("idOras")),
                    Double.parseDouble(map.get("valoare")));
            Integer resultId = temperaturesService.addTemperature(temperatures);
            return UtilsHw.mapId(resultId);
        }
        catch (NumberFormatException nfe)
        {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<TemperaturesDTO>> getTemperatures(
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lon,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date until) {

        List<TemperaturesDTO> temperaturesDTOList = temperaturesService.getTemperatures(lat, lon, from, until).stream()
                .map(temp -> new TemperaturesDTO(temp))
                .collect(Collectors.toList());

        return new ResponseEntity<>(temperaturesDTOList, HttpStatus.OK);
    }

    @GetMapping("/cities/{id}")
    public ResponseEntity<List<TemperaturesDTO>> getCityTemperatures(
            @PathVariable("id") Integer cityId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date until) {

        List<TemperaturesDTO> temperaturesDTOList = temperaturesService.getCityTemperatures(cityId, from, until)
                .stream().map(temp -> new TemperaturesDTO(temp))
                .collect(Collectors.toList());
        return new ResponseEntity<>(temperaturesDTOList, HttpStatus.OK);
    }

    @GetMapping("/countries/{id}")
    public ResponseEntity<List<TemperaturesDTO>> getCountryTemperatures(
            @PathVariable("id") Integer countryId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date until) {

        List<TemperaturesDTO> temperaturesDTOList = temperaturesService.getCountryTemperatures(countryId, from, until)
                .stream().map(temp -> new TemperaturesDTO(temp))
                .collect(Collectors.toList());
        return new ResponseEntity<>(temperaturesDTOList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateById(@PathVariable("id") Integer id, @RequestBody Map<String, String> map) {

        if (UtilsHw.hasNullParameters(map, Arrays.asList("id", "idOras", "valoare")))
            return new ResponseEntity<>("Missing parameters in request body.", HttpStatus.BAD_REQUEST);

        try {
            // Verificarea id-ului din PathVariable sa corespunda cu id-ul din body
            if (id != Integer.parseInt(map.get("id")))
                return new ResponseEntity<>("Path variable id and the id in the request body do not match.",
                        HttpStatus.BAD_REQUEST);

            // Creare obiect cu parametrii din request body
            Temperatures updateTemperature = new Temperatures(Integer.parseInt(map.get("idOras")),Double.parseDouble(map.get("valoare")));
            updateTemperature.setId(id);

            if (temperaturesService.updateEntryById(updateTemperature))
                return new ResponseEntity<>("Temperature updated successfully.", HttpStatus.OK);
            else
                return new ResponseEntity<>("Temperature not found.", HttpStatus.NOT_FOUND);
        }
        catch(NumberFormatException nfe){
            return new ResponseEntity<>("Wrong format of parameters.",HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") Integer id) {

        if (temperaturesService.deleteEntryById(id))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
