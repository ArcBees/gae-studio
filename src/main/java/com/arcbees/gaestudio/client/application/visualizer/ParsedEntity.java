package com.arcbees.gaestudio.client.application.visualizer;

import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

import java.util.Set;

public class ParsedEntity {

    private static final String PROPERTY_MAP = "propertyMap";

    private EntityDto entityDTO;
    private JSONObject jsonObject;

    public ParsedEntity(EntityDto entityDTO) {
        this.entityDTO = new EntityDto(entityDTO.getKey(), entityDTO.getJson());

        parseJson();
    }

    public Set<String> propertyKeys() {
        return getPropertyMap().keySet();
    }

    public Boolean hasProperty(String key) {
        return getPropertyMap().containsKey(key);
    }

    public JSONValue getProperty(String key) {
        return getPropertyMap().get(key);
    }

    public JSONObject getPropertyMap() {
        return jsonObject.get(PROPERTY_MAP).isObject();
    }

    public void setEntityDTO(EntityDto entityDTO) {
        this.entityDTO = entityDTO;

        parseJson();
    }

    public EntityDto getEntityDTO() {
        return entityDTO;
    }

    public KeyDto getKey() {
        return entityDTO.getKey();
    }

    public String getJson() {
        return entityDTO.getJson();
    }

    public void parseJson() {
        jsonObject = JSONParser.parseStrict(getJson()).isObject();
    }

}
