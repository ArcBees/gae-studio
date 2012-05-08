package com.arcbees.gaestudio.server.dto.mapper;

import com.arcbees.gaestudio.shared.dto.entity.Entity;
import com.arcbees.gaestudio.shared.dto.entity.Key;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class EntityMapper {
    
    @SuppressWarnings("unused")
    private EntityMapper() {
    }
    
    public static Entity mapDTO(com.google.appengine.api.datastore.Entity dbEntity) {
        // TODO see about reusing the Gson instance
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        return new Entity(mapKey(dbEntity.getKey()), gson.toJson(dbEntity));
    }
    
    private static Key mapKey(com.google.appengine.api.datastore.Key dbKey) {
        return new Key(dbKey.getKind(), dbKey.getId());
    }
    
}
