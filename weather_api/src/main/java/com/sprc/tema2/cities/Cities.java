package com.sprc.tema2.cities;

import org.springframework.data.annotation.Id;

public class Cities {
    @Id
    private Integer id;

    private Integer idTara;
    private String nume;
    private Double lat;
    private Double lon;

    public Cities(Integer idTara, String nume, Double lat, Double lon){
        this.idTara = idTara;
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

    public Integer getIdTara() {
        return idTara;
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
