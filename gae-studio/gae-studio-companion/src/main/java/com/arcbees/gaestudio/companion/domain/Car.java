/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.companion.domain;

import java.util.List;

import com.arcbees.gaestudio.companion.dao.HasId;
import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Car implements HasId {
    @Id
    private Long id;
    private String make;
    private List<Boolean> booleans;
    private List<GeoPt> geoPts;
    private List<Wheel> wheels;
    private List<Object> mixedProperties;

    public List<Object> getMixedProperties() {
        return mixedProperties;
    }

    public void setMixedProperties(List<Object> mixedProperties) {
        this.mixedProperties = mixedProperties;
    }

    public List<Wheel> getWheels() {
        return wheels;
    }

    public void setWheels(List<Wheel> wheels) {
        this.wheels = wheels;
    }

    public List<GeoPt> getGeoPts() {
        return geoPts;
    }

    public void setGeoPts(List<GeoPt> geoPts) {
        this.geoPts = geoPts;
    }

    public List<Boolean> getBooleans() {
        return booleans;
    }

    public void setBooleans(List<Boolean> booleans) {
        this.booleans = booleans;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
