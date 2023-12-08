package com.sprc.tema2.temperatures;

import java.util.List;

public interface TemperaturesService {
    Integer addTemperature(Temperatures temperatures);
    List<Temperatures> getTemperatures();
}
