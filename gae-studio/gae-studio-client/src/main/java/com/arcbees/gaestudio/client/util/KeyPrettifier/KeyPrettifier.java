package com.arcbees.gaestudio.client.util.KeyPrettifier;

import com.arcbees.gaestudio.shared.PropertyName;
import com.arcbees.gaestudio.shared.PropertyType;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

public class KeyPrettifier {
    public String writeParentKeys(JSONObject propertyValue) {
        String returnValue = "";

        JSONValue parentKeyJson = propertyValue.get(PropertyName.PARENT_KEY);
        JSONObject jsonObject = parentKeyJson.isObject();

        if (jsonObject != null) {
            String kind = jsonObject.get(PropertyName.KIND).isString().stringValue();

            String id = jsonObject.get(PropertyName.ID).toString();
            String name = jsonObject.get(PropertyName.NAME).toString();

            String idName = getIdName(id, name);

            returnValue += writeParentKeys(jsonObject) + kind + " (" + idName + ") > ";
        }

        return returnValue;
    }

    public String prettifyKey(JSONValue jsonProperty, String stringValue) {
        JSONObject jsonObject = jsonProperty.isObject();
        String value = stringValue;

        if (jsonObject != null) {
            JSONValue jsonPropertyType = jsonObject.get(PropertyName.GAE_PROPERTY_TYPE);

            if (jsonPropertyType != null) {
                PropertyType propertyType = PropertyType.valueOf(jsonPropertyType.isString().stringValue());

                if (propertyType == PropertyType.KEY) {
                    JSONObject propertyValue = jsonObject.get(PropertyName.VALUE).isObject();

                    String parentValue = writeParentKeys(propertyValue);

                    String kind = propertyValue.get(PropertyName.KIND).isString().stringValue();

                    String id = propertyValue.get(PropertyName.ID).toString();
                    String name = propertyValue.get(PropertyName.NAME).toString();
                    String idName = getIdName(id, name);

                    value = parentValue + kind + " (" + idName + ")";
                }
            }
        }

        return value;
    }

    public String prettifyKey(KeyDto key) {
        String kind = key.getKind();
        String idName = getIdName(key.getId().toString(), key.getName());

        String parentKind = key.getParentKey() != null ? key.getParentKey().getKind() : null;
        Long parentKindId = parentKind != null ? key.getParentKey().getId() : null;

        String result = kind + " (" + idName + ")";
        if (parentKind != null) {
            result += " > " + parentKind + " (" + parentKindId + ")";
        }

        return result;
    }

    private String getIdName(String id, String name) {
        return id.equals("0") ? name : id;
    }
}
