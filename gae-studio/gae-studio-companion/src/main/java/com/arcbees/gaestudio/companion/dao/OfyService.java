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
