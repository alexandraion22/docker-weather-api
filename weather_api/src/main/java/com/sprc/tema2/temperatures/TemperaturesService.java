package com.sprc.tema2.temperatures;

import java.util.Date;
import java.util.List;

public interface TemperaturesService {
    Integer addTemperature(Temperatures temperatures);
    List<Temperatures> getTemperatures(Double lat, Double lon, Date from, Date until);
    List<Temperatures> getCityTemperatures(Integer cityId, Date from, Date until);
}