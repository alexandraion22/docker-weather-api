package com.sprc.tema2.countries;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Countries{
    @Id
    private String nume;
    private Double lat;
    private Double lon;

    public Countries() {

    }

    public Countries(String nume, Double lat, Double lon) {
        this.nume = nume;
        this.lat = lat;
        this.lon = lon;
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