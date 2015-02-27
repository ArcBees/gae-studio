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
