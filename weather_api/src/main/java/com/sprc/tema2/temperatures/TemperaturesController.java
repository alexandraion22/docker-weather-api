package com.sprc.tema2.temperatures;

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

        Temperatures temperatures = new Temperatures(Integer.valueOf(map.get("idOras")),
                Double.valueOf(map.get("valoare")));

        Integer resultId = temperaturesService.addTemperature(temperatures);

        if (resultId != null) {
            if (resultId == -1)
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(new HashMap<>() {
                {
                    put("id", resultId);
                }
            }, HttpStatus.CREATED);
        } else
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
    }

    @GetMapping
    public ResponseEntity<List<TemperaturesDTO>> getTemperatures(
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lon,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date until) {

        List<TemperaturesDTO> temperaturesDTOList = temperaturesService.getTemperatures(lat, lon, from, until).stream()
                .map(temp -> {
                    return new TemperaturesDTO(temp.getId(), temp.getValoare(), temp.getTimestamp());
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(temperaturesDTOList, HttpStatus.OK);
    }

    @GetMapping("/cities/{id}")
    public ResponseEntity<List<TemperaturesDTO>> getCityTemperatures(
            @PathVariable("id") Integer cityId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date until) {
        if (cityId == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        List<TemperaturesDTO> temperaturesDTOList = temperaturesService.getCityTemperatures(cityId, from, until)
                .stream()
                .map(temp -> {
                    return new TemperaturesDTO(temp.getId(), temp.getValoare(), temp.getTimestamp());
                })
                .collect(Collectors.toList());

        return new ResponseEntity<>(temperaturesDTOList, HttpStatus.OK);
    }

}
