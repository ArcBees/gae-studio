package com.arcbees.gaestudio.server.dto.mapper;

import com.arcbees.gaestudio.shared.dto.entity.EntityDTO;
import com.arcbees.gaestudio.shared.dto.entity.KeyDTO;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class EntityMapper {
    
    @SuppressWarnings("unused")
    private EntityMapper() {
    }
    
    public static EntityDTO mapDTO(Entity dbEntity) {
        // TODO see about reusing the Gson instance
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        return new EntityDTO(mapKey(dbEntity.getKey()), gson.toJson(dbEntity));
    }
    
    private static KeyDTO mapKey(Key dbKey) {
        return new KeyDTO(dbKey.getKind(), dbKey.getId());
    }
    
}
