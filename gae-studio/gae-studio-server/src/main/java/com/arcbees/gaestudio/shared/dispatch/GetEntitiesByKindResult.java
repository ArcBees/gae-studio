/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.shared.dispatch;

import java.util.ArrayList;

import com.gwtplatform.dispatch.shared.Result;

public class GetEntitiesByKindResult implements Result {
    private ArrayList<com.arcbees.gaestudio.server.dto.entity.EntityDto> entities;

    protected GetEntitiesByKindResult() {
        // Possibly for serialization.
    }

    public GetEntitiesByKindResult(ArrayList<com.arcbees.gaestudio.server.dto.entity.EntityDto> entities) {
        this.entities = entities;
    }

    public ArrayList<com.arcbees.gaestudio.server.dto.entity.EntityDto> getEntities() {
        return entities;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GetEntitiesByKindResult other = (GetEntitiesByKindResult) obj;
        if (entities == null) {
            if (other.entities != null)
                return false;
        } else if (!entities.equals(other.entities))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 23;
        hashCode = (hashCode * 37) + (entities == null ? 1 : entities.hashCode());
        return hashCode;
    }

    @Override
    public String toString() {
        return "GetEntitiesByKindResult["
                + entities
                + "]";
    }
}
