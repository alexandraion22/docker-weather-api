package com.sprc.tema2.cities;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitiesRepository extends MongoRepository<Cities, String> {
    Cities findByIdTaraAndNume(Integer idTara, String nume);
}