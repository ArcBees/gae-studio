/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.companion.dao;

import com.arcbees.gaestudio.companion.domain.Business;
import com.arcbees.gaestudio.companion.domain.Car;
import com.arcbees.gaestudio.companion.domain.Concept;
import com.arcbees.gaestudio.companion.domain.Manufacturer;
import com.arcbees.gaestudio.companion.domain.StringIdEntity;
import com.arcbees.gaestudio.companion.domain.Vehicle;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class OfyService {
    static {
        factory().register(Car.class);
        factory().register(StringIdEntity.class);
        factory().register(Vehicle.class);
        factory().register(Manufacturer.class);
        factory().register(Business.class);
        factory().register(Concept.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}
