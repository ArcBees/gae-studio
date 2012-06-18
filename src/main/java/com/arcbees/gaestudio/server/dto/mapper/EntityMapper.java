package com.arcbees.gaestudio.server.dto.mapper;

import com.arcbees.gaestudio.shared.dto.entity.EntityDTO;
import com.arcbees.gaestudio.shared.dto.entity.KeyDTO;
import com.arcbees.gaestudio.shared.dto.entity.ParentKeyDTO;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.GsonDatastoreFactory;
import com.google.appengine.api.datastore.Key;
import com.google.gson.Gson;

public class EntityMapper {
    
    @SuppressWarnings("unused")
    private EntityMapper() {
    }
    
    public static EntityDTO mapDTO(Entity dbEntity) {
        Gson gson = GsonDatastoreFactory.create();
        return new EntityDTO(mapKey(dbEntity.getKey()), gson.toJson(dbEntity));
    }
    
    private static KeyDTO mapKey(Key dbKey) {
        return new KeyDTO(dbKey.getKind(), dbKey.getId(), mapParentKey(dbKey.getParent()));
    }

    private static ParentKeyDTO mapParentKey(Key dbParentKey){
        if(dbParentKey == null){
            return null;
        }
        return new ParentKeyDTO(dbParentKey.getKind(), dbParentKey.getId());
    }
    
}
