package com.arcbees.gaestudio.client.domain;

import com.arcbees.gaestudio.shared.dto.entity.EntityDTO;
import com.arcbees.gaestudio.shared.dto.entity.KeyDTO;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

import java.util.Set;

public class EntityJsonParsed {

    private final EntityDTO entityDTO;
    private JSONObject jsonObject;

    public EntityJsonParsed(EntityDTO entityDTO){
        this.entityDTO = new EntityDTO(entityDTO.getKey(), entityDTO.getJson());

        parseJson();
    }

    public Set<String> propertyKeys(){
        return getPropertyMap().keySet();
    }

    public Boolean hasProperty(String key){
        return getPropertyMap().containsKey(key);
    }

    public JSONValue getProperty(String key){
        return getPropertyMap().get(key);
    }

    public JSONObject getPropertyMap(){
        return jsonObject.get("propertyMap").isObject();
    }

    public EntityDTO getEntityDTO() {
        return entityDTO;
    }

    public KeyDTO getKey() {
        return entityDTO.getKey();
    }

    public String getJson() {
        return entityDTO.getJson();
    }

    public void parseJson() {
        jsonObject = JSONParser.parseStrict(getJson()).isObject();
    }
}
