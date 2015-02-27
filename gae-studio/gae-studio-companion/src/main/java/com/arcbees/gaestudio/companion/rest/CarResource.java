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

package com.arcbees.gaestudio.companion.rest;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.arcbees.gaestudio.companion.dao.BusinessDao;
import com.arcbees.gaestudio.companion.dao.CarDao;
import com.arcbees.gaestudio.companion.dao.ConceptDao;
import com.arcbees.gaestudio.companion.dao.ManufacturerDao;
import com.arcbees.gaestudio.companion.dao.VehicleDao;
import com.arcbees.gaestudio.companion.domain.Business;
import com.arcbees.gaestudio.companion.domain.Car;
import com.arcbees.gaestudio.companion.domain.Concept;
import com.arcbees.gaestudio.companion.domain.Manufacturer;
import com.arcbees.gaestudio.companion.domain.Vehicle;
import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;

@Path(TestEndPoints.CAR)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CarResource {
    private final CarDao carDao;
    private final VehicleDao vehicleDao;
    private final ManufacturerDao manufacturerDao;
    private final BusinessDao businessDao;
    private final ConceptDao conceptDao;

    @Inject
    CarResource(CarDao carDao,
            VehicleDao vehicleDao,
            ManufacturerDao manufacturerDao,
            BusinessDao businessDao,
            ConceptDao conceptDao) {
        this.carDao = carDao;
        this.vehicleDao = vehicleDao;
        this.manufacturerDao = manufacturerDao;
        this.businessDao = businessDao;
        this.conceptDao = conceptDao;
    }

    @POST
    public Response createCar(Car car) {
        car.setId(null);

        if (car.getMixedProperties() != null) {
            convertGeoPts(car);
        }

        carDao.put(car);

        return Response.ok(car.getId()).build();
    }

    @Path(TestEndPoints.HIERARCHY)
    @POST
    public Response createCarWithHierarchy(Car car) {
        car.setId(null);

        if (car.getMixedProperties() != null) {
            convertGeoPts(car);
        }

        setVehicleParent(car);
        setManufacturerRef(car);

        carDao.put(car);

        return Response.ok(car.getId()).build();
    }

    @GET
    public Response getCar(@QueryParam(TestEndPoints.PARAM_ID) Long id) {
        Car car = carDao.get(id);

        return car != null ? Response.ok(car).build() : Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    public Response deleteCar(Long id) {
        carDao.delete(id);

        return Response.noContent().build();
    }

    private void convertGeoPts(Car car) {
        List<Object> mixedProperties = car.getMixedProperties();
        for (int i = 0; i < mixedProperties.size(); i++) {
            Object obj = mixedProperties.get(i);
            if (isGeoPt(obj)) {
                LinkedHashMap<String, Double> map = (LinkedHashMap<String, Double>) obj;

                double latitutde = map.get("latitude");
                double longitude = map.get("longitude");

                mixedProperties.set(i, new GeoPt(new Float(latitutde), new Float(longitude)));
            }
        }
    }

    private boolean isGeoPt(Object obj) throws ClassCastException {
        boolean isGeoPt = false;

        try {
            LinkedHashMap<String, Double> map = (LinkedHashMap<String, Double>) obj;
            Set<String> keys = map.keySet();
            if (keys.contains("latitude") && keys.contains("longitude")) {
                isGeoPt = true;
            }
        } catch (ClassCastException e) {
            isGeoPt = false;
        }

        return isGeoPt;
    }

    private void setVehicleParent(Car car) {
        Vehicle vehicle = new Vehicle();
        vehicleDao.put(vehicle);

        car.setVehicleKey(Key.create(Vehicle.class, vehicle.getId()));
    }

    private void setManufacturerRef(Car car) {
        Concept concept = new Concept();
        conceptDao.put(concept);

        Business business = new Business();
        Key<Concept> conceptKey = Key.create(Concept.class, concept.getId());
        business.setConceptKey(conceptKey);
        businessDao.put(business);

        Manufacturer manufacturer = new Manufacturer();
        Key<Business> businessKey = Key.create(conceptKey, Business.class, business.getId());
        manufacturer.setBusinessKey(businessKey);
        manufacturerDao.put(manufacturer);

        car.setManufacturerRef(Ref.create(Key.create(businessKey, Manufacturer.class, manufacturer.getId())));
    }
}
