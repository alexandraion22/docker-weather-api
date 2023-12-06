package com.sprc.tema2.countries;

import java.util.List;

public interface CountriesService {
    boolean addCountry(Countries country);

    List<Countries> getCountries();

    boolean updateEntryById(Integer id, Countries country);

    boolean deleteEntryById(Integer id);
}