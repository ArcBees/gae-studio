package com.arcbees.gaestudio.companion.dao;

import com.arcbees.gaestudio.companion.domain.Car;

import static com.arcbees.gaestudio.companion.dao.OfyService.ofy;

public class CarDao extends BaseDao<Car> {
    CarDao() {
        super(Car.class);
    }

    @Override
    public Car get(Long id) {
        return ofy().load().type(clazz).id(id).now();
    }
}
