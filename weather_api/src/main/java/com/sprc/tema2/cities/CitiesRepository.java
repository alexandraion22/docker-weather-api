package com.sprc.tema2.cities;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitiesRepository extends MongoRepository<Cities, String> {
    Cities findByIdTaraAndNume(Integer idTara, String nume);
    List<Cities> findAllByIdTara(Integer id);
    Cities findById(Integer id);
    void deleteById(Integer id);
    Cities findByNume(String nume);
}