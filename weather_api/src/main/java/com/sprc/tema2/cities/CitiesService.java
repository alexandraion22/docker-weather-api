package com.sprc.tema2.cities;

import java.util.List;

public interface CitiesService {
    Integer addCity(Cities city);

    List<Cities> getCities();

    List<Cities> getCitiesByCountryId(Integer id);

    Integer updateEntryById(Cities updatedCity);

    boolean deleteEntryById(Integer id);

    Cities getEntryById(Integer id);
}
