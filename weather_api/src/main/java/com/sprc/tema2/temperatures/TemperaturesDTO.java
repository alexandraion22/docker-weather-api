package com.sprc.tema2.temperatures;

import java.util.Date;

public class TemperaturesDTO {
    private Integer id;
    private Double valoare;
    Date timestamp;

    public TemperaturesDTO(Integer id, Double valoare, Date timestamp) {
        this.id = id;
        this.valoare = valoare;
        this.timestamp = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public Double getValoare() {
        return valoare;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
