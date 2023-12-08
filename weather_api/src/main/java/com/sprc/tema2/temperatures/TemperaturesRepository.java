package com.sprc.tema2.temperatures;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;

@Repository
public interface TemperaturesRepository extends MongoRepository<Temperatures,String> {
    Temperatures findByIdOrasAndTimestamp(Integer idOras, LocalDateTime timestamp);
}
