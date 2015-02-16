/**
 * Copyright 2015 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.gaestudio.companion.domain;

import java.util.List;

import com.arcbees.gaestudio.companion.dao.HasId;
import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class Car implements HasId {
    @Id
    private Long id;
    @Index
    private String make;
    private List<Boolean> booleans;
    private List<GeoPt> geoPts;
    private List<Wheel> wheels;
    private List<Object> mixedProperties;
    @Parent
    private Key<Vehicle> vehicleKey;
    private Ref<Manufacturer> manufacturerRef;

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

    public Key<Vehicle> getVehicleKey() {
        return vehicleKey;
    }

    public void setVehicleKey(Key<Vehicle> vehicleKey) {
        this.vehicleKey = vehicleKey;
    }

    public Ref<Manufacturer> getManufacturerRef() {
        return manufacturerRef;
    }

    public void setManufacturerRef(Ref<Manufacturer> manufacturerRef) {
        this.manufacturerRef = manufacturerRef;
    }
}
