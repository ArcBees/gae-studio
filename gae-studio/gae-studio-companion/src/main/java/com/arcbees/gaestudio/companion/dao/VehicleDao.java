/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.companion.dao;

import com.arcbees.gaestudio.companion.domain.Vehicle;

import static com.arcbees.gaestudio.companion.dao.OfyService.ofy;

public class VehicleDao extends BaseDao<Vehicle> {
    VehicleDao() {
        super(Vehicle.class);
    }

    @Override
    public Vehicle get(Long id) {
        return ofy().load().type(clazz).id(id).now();
    }
}
