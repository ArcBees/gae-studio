/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.factories;

import java.util.List;

import com.arcbees.gaestudio.companion.domain.Car;
import com.arcbees.gaestudio.companion.domain.Wheel;
import com.google.appengine.api.datastore.GeoPt;
import com.google.common.collect.Lists;

public class CarFactory {
    private static final GeoPt POINT1 = new GeoPt(0.0f, 0.0f);
    private static final GeoPt POINT2 = new GeoPt(0.0f, 0.0f);
    private static final GeoPt POINT3 = new GeoPt(10.0f, 10.0f);
    private static final GeoPt POINT4 = new GeoPt(10.0f, 10.0f);
    private static final double WHEEL_SIZE = 5.25;
    private static final double WHEEL_SIZE2 = 10.0;
    private static final double WHEEL_SIZE3 = 20.75;

    private static final String MAKE = "Ford";

    public Car createFakeCar() {
        Car car = new Car();

        car.setMake(MAKE);
        car.setBooleans(Lists.newArrayList(true, true, true));
        car.setGeoPts(Lists.newArrayList(POINT1, POINT2, POINT3));
        car.setWheels(Lists.newArrayList(new Wheel(WHEEL_SIZE), new Wheel(WHEEL_SIZE2), new Wheel(WHEEL_SIZE3)));

        List<Object> mixedProperties = Lists.<Object>newArrayList(1, "some string", POINT4);
        car.setMixedProperties(mixedProperties);

        return car;
    }
}
