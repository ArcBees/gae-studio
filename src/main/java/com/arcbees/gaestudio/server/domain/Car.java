package com.arcbees.gaestudio.server.domain;

import com.google.appengine.api.datastore.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Car {

    @Id
    private Long id;
    private String manufacturer;
    private String model;
    private Integer year;
    private Key driverKey;

    public Car() {
    }

    public Car(String manufacturer, String model, Integer year, Key driverKey) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.year = year;
        this.driverKey = driverKey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Key getDriverKey() {
        return driverKey;
    }

    public void setDriverKey(Key driverKey) {
        this.driverKey = driverKey;
    }

}
