package com.sprc.tema2.countries;

import java.util.List;

public interface CountriesService {
    Integer addCountry(Countries country);
    List<Countries> getCountries();
    boolean updateEntryById(Countries updatedCountry);
    boolean deleteEntryById(Integer id);
    Countries getEntryById(Integer id);
}