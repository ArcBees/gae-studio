/**
 * Copyright 2015 ArcBees Inc.
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

package com.arcbees.gaestudio.client.util.keyprettifier;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.resources.AppMessages;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;

public class KeyPrettifier {
    private final AppMessages appMessages;

    @Inject
    KeyPrettifier(
            AppMessages appMessages) {
        this.appMessages = appMessages;
    }

    public String prettifyKey(KeyDto key) {
        if (key == null) {
            return "";
        }

        String parentValue = writeParentKeys(key);

        String kind = key.getKind();

        long id = key.getId();
        String name = key.getName();
        String idName = getIdName(id, name);

        return parentValue + appMessages.keyPrettifyTemplate(kind, idName);
    }

    private String writeParentKeys(KeyDto key) {
        String returnValue = "";
        KeyDto parentKey = key.getParentKey();

        if (parentKey != null) {
            String kind = parentKey.getKind();

            long id = parentKey.getId();
            String name = parentKey.getName();

            String idName = getIdName(id, name);

            returnValue += writeParentKeys(parentKey)
                    + appMessages.keyPrettifyTemplate(kind, idName)
                    + appMessages.keyPrettifyChildToken();
        }

        return returnValue;
    }

    private String getIdName(long id, String name) {
        return 0L == id ? name : String.valueOf(id);
    }
}
