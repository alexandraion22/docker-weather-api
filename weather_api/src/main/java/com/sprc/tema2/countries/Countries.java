package com.sprc.tema2.countries;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "countries")
public class Countries {
    @Id
    private Integer id;
    private String nume;
    private Double lat;
    private Double lon;

    public Countries(String nume, Double lat, Double lon) {
        this.nume = nume;
        this.lat = lat;
        this.lon = lon;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }
}