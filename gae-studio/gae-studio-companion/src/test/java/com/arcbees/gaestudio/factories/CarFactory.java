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
