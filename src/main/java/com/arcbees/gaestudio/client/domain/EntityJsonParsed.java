package com.arcbees.gaestudio.client.domain;

import com.arcbees.gaestudio.shared.dto.entity.EntityDTO;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

import java.util.Set;

public class EntityJsonParsed extends EntityDTO {
    private transient JSONObject jsonObject;

    public EntityJsonParsed(EntityDTO entityDTO){
        super(entityDTO.getKey(), entityDTO.getJson());

        parseJson();
    }

    @Override
    public void setJson(String json) {
        super.setJson(json);

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

    private void parseJson() {
        jsonObject = JSONParser.parseStrict(getJson()).isObject();
    }

    private JSONObject getPropertyMap(){
        return jsonObject.get("propertyMap").isObject();
    }
}
