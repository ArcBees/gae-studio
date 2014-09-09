/*
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.util.KeyPrettifier;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.resources.AppMessages;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;

public class KeyPrettifier {
    private final AppMessages appMessages;

    @Inject
    public KeyPrettifier(AppMessages appMessages) {
        this.appMessages = appMessages;
    }

    public String prettifyKey(KeyDto key) {
        String parentValue = writeParentKeys(key);

        String kind = key.getKind();

        String id = key.getId().toString();
        String name = key.getName();
        String idName = getIdName(id, name);

        return parentValue + appMessages.keyPrettifyTemplate(kind, idName);
    }

    private String writeParentKeys(KeyDto key) {
        String returnValue = "";
        KeyDto parentKey = key.getParentKey();

        if (parentKey != null) {
            String kind = parentKey.getKind();

            String id = parentKey.getId().toString();
            String name = parentKey.getName();

            String idName = getIdName(id, name);

            returnValue += writeParentKeys(parentKey)
                    + appMessages.keyPrettifyTemplate(kind, idName)
                    + appMessages.keyPrettifyChildToken();
        }

        return returnValue;
    }

    private String getIdName(String id, String name) {
        return id.equals("0") ? name : id;
    }
}
