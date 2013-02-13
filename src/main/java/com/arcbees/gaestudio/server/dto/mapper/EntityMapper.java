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
