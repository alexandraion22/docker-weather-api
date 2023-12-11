package com.sprc.tema2.temperatures;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TemperaturesRepository extends MongoRepository<Temperatures, String> {
    Temperatures findByIdOrasAndTimestamp(Integer idOras, Date timestamp);

    List<Temperatures> findAllByIdOras(Integer isOras);
}
