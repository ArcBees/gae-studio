/**
 * Copyright 2013 ArcBees Inc.
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

package com.arcbees.gaestudio.server.dto.mapper;

import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.arcbees.gaestudio.shared.dto.entity.ParentKeyDto;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.GsonDatastoreFactory;
import com.google.appengine.api.datastore.Key;
import com.google.gson.Gson;

public class EntityMapper {
    @SuppressWarnings("unused")
    private EntityMapper() {
    }
    
    public static EntityDto mapDTO(Entity dbEntity) {
        Gson gson = GsonDatastoreFactory.create();
        return new EntityDto(mapKey(dbEntity.getKey()), gson.toJson(dbEntity));
    }
    
    private static KeyDto mapKey(Key dbKey) {
        return new KeyDto(dbKey.getKind(), dbKey.getId(), mapParentKey(dbKey.getParent()));
    }

    private static ParentKeyDto mapParentKey(Key dbParentKey){
        if(dbParentKey == null){
            return null;
        }
        return new ParentKeyDto(dbParentKey.getKind(), dbParentKey.getId());
    }    
}
