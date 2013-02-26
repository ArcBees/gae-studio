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

package com.arcbees.gaestudio.client.application.visualizer.ui;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.inject.assistedinject.Assisted;

public class PropertyColumn extends TextColumn<ParsedEntity> {
    private String property;

    @Inject
    public PropertyColumn(@Assisted String property) {
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
