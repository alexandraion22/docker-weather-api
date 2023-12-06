package com.sprc.tema2.countries;

import com.sprc.tema2.countries.Countries;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountriesRepository extends MongoRepository<Countries, String> {
    Countries findByNume(String nume);
    Countries findById(Integer id);
    void deleteById(Integer id);
}