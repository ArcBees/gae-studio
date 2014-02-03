package com.arcbees.gaestudio.factories;

import com.arcbees.gaestudio.companion.domain.Car;
import com.arcbees.gaestudio.companion.domain.Wheel;
import com.google.appengine.api.datastore.GeoPt;
import com.google.common.collect.Lists;

import java.util.List;

public class CarFactory {

    public Car createFakeCar() {
        Car car = new Car();
        String make = "Ford";
        car.setMake(make);
        car.setBooleans(Lists.newArrayList(true, true, true));
        car.setGeoPts(Lists.newArrayList(new GeoPt(10.5f, 10.5f), new GeoPt(0.0f, 0.0f),
                new GeoPt(-10.12345f, -10.12345f)));
        car.setWheels(Lists.newArrayList(new Wheel(5.25), new Wheel(10.0), new Wheel(20.75)));

        List<Object> mixedProperties = Lists.<Object>newArrayList(1, "some string", new GeoPt(10.0f, 10.0f));
        car.setMixedProperties(mixedProperties);

        return car;
    }

}
