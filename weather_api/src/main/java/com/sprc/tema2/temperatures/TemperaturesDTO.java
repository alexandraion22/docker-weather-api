package com.sprc.tema2.temperatures;

import java.util.Date;

public class TemperaturesDTO {
    private Integer id;
    private Double valoare;
    Date timestamp;

    public TemperaturesDTO(Temperatures temperatures) {
        this.id = temperatures.getId();
        this.valoare = temperatures.getValoare();
        this.timestamp = temperatures.getTimestamp();
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
