package com.arcbees.gaestudio.client.application.visualizer.entitylist;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.cellview.client.TextColumn;

public class PropertyColumn extends TextColumn<ParsedEntity> {

    private String property;

    public PropertyColumn(String property) {
        this.property = property;
    }

    @Override
    public String getValue(ParsedEntity parsedEntity) {
        if (parsedEntity.hasProperty(property)) {
            JSONValue value = parsedEntity.getProperty(property);
            if (value == null) {
                return "<null>";
            } else if (value.isObject() != null) {
                JSONObject object = value.isObject();
                if (object.containsKey("kind") && object.containsKey("id")) {
                    return object.get("kind").isString().stringValue() + ", " + object.get("id");
                }
            } else if (value.isString() != null) {
                return value.isString().stringValue();
            }
            return displayUnhandledTypeField(value);
        } else {
            return "<missing>";
        }
    }

    private String displayUnhandledTypeField(JSONValue value) {
        String text = value.toString();
        if (text.length() > 20) {
            return text.substring(0, 20) + "...";
        }
        return text;
    }

}
