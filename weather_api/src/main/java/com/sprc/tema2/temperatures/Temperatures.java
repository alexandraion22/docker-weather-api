package com.sprc.tema2.temperatures;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Date;

public class Temperatures {
    @Id
    private Integer id;
    private Integer idOras;
    private Double valoare;
    LocalDateTime timestamp;

    public Temperatures(Integer idOras, Double valoare){
        this.idOras = idOras;
        this.valoare = valoare;
        this.timestamp = LocalDateTime.now();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getIdOras() {
        return idOras;
    }

    public Double getValoare() {
        return valoare;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
