package com.sprc.tema2.cities;

import java.util.List;

public interface CitiesService {
    Integer addCity(Cities city);

    List<Cities> getCities();
}
