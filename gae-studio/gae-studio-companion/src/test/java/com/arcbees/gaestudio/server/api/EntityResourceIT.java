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

package com.arcbees.gaestudio.server.api;

import org.junit.Test;

import com.arcbees.gaestudio.companion.domain.Car;
import com.arcbees.gaestudio.companion.rest.TestEndPoints;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.rest.EndPoints;
import com.google.appengine.api.datastore.KeyFactory;
import com.jayway.restassured.response.Response;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;
import static javax.ws.rs.core.Response.Status.OK;

import static org.junit.Assert.assertEquals;

import static com.jayway.restassured.RestAssured.given;

public class EntityResourceIT extends RestIT {
    private static final Long UNEXISTENT_ID = 99999999L;

    @Test
    public void getEntity_createCar_shouldReturnEntity() {
        // given
        Long carId = createRemoteCar();

        // when
        Response response = getCarEntityResponse(carId);
        EntityDto entityDto = responseToEntityDto(response);

        // then
        assertEquals(OK.getStatusCode(), response.getStatusCode());
        assertEquals(CAR_KIND, entityDto.getKey().getKind());
        assertEquals((long) carId, (long) entityDto.getKey().getId());
    }

    @Test
    public void getEntity_createStringIdEntity_shouldReturnEntity() throws InterruptedException {
        // given
        createStringIdEntity(AN_ENTITY_NAME);

        // when
        Response response = getStringIdEntityResponse(AN_ENTITY_NAME);
        EntityDto entityDto = responseToEntityDto(response);

        // then
        assertEquals(OK.getStatusCode(), response.getStatusCode());
        assertEquals(STRING_ENTITY_KIND, entityDto.getKey().getKind());
        assertEquals(AN_ENTITY_NAME, entityDto.getKey().getName());
    }

    @Test
    public void getUnexistentEntity_shouldReturnNotFound() {
        // when
        Response response = getCarEntityResponse(UNEXISTENT_ID);

        // then
        assertEquals(NOT_FOUND.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void deleteCar_createCar_shouldReturnNoContent() {
        // given
        Long carId = createRemoteCar();

        // when
        Response response = deleteEntityResponse(carId);

        // then
        assertEquals(NO_CONTENT.getStatusCode(), response.getStatusCode());
    }

    @Test
    public void updateCar_createCar_shouldReturnOK() {
        // given
        Car car = new Car();
        car.setMake("OldMake");
        Long carId = createRemoteCar(car);

        // when
        Response response = getCarEntityResponse(carId);
        EntityDto entityDto = responseToEntityDto(response);
        String newJson = entityDto.getJson().replaceFirst("OldMake", "NewMake");
        entityDto.setJson(newJson);

        Response updateResponse = updateEntityResponse(entityDto);

        // then
        assertEquals(OK.getStatusCode(), updateResponse.getStatusCode());
    }

    private Response updateEntityResponse(EntityDto newEntityDto) {
        return given()
                .body(newEntityDto)
                .put(getAbsoluteUri(EndPoints.ENTITY));
    }

    private Response deleteEntityResponse(Long carId) {
        return given()
                .queryParam(TestEndPoints.PARAM_KEY, getEncodedCarKey(carId))
                .delete(getAbsoluteUri(EndPoints.ENTITY));
    }

    private String getEncodedCarKey(Long carId) {
        return KeyFactory.createKeyString(CAR_KIND, carId);
    }

    private String getEncodedStringIdKey(String name) {
        return KeyFactory.createKeyString(STRING_ENTITY_KIND, name);
    }

    private EntityDto responseToEntityDto(Response response) {
        return gson.fromJson(response.asString(), EntityDto.class);
    }

    private Response getCarEntityResponse(Long carId) {
        return given()
                .queryParam(TestEndPoints.PARAM_KEY, getEncodedCarKey(carId))
                .get(getAbsoluteUri(EndPoints.ENTITY));
    }

    private Response getStringIdEntityResponse(String name) {
        return given()
                .queryParam(TestEndPoints.PARAM_KEY, getEncodedStringIdKey(name))
                .get(getAbsoluteUri(EndPoints.ENTITY));
    }
}
