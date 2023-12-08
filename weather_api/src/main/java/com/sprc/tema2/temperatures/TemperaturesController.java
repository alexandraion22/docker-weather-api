package com.sprc.tema2.temperatures;

import com.sprc.utils.UtilsHw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/temperatures")
@CrossOrigin
public class TemperaturesController {
    @Autowired
    TemperaturesService temperaturesService;

    @PostMapping
    ResponseEntity<Map<String,Integer>> addTemperature (@RequestBody Map<String,String> map){

        if (UtilsHw.hasNullParameters(map, Arrays.asList("idOras", "valoare")))
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        Temperatures temperatures = new Temperatures(Integer.valueOf(map.get("idOras")), Double.valueOf(map.get("valoare")));

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
    public ResponseEntity<List<Temperatures>> getTemperatures() {
        return new ResponseEntity<>(temperaturesService.getTemperatures(), HttpStatus.OK);
    }
}
