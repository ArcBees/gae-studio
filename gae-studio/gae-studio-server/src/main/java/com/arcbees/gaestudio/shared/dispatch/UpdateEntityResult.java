/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.shared.dispatch;

import com.arcbees.gaestudio.server.dto.entity.EntityDto;
import com.gwtplatform.dispatch.shared.Result;

public class UpdateEntityResult implements Result {
    private EntityDto result;

    protected UpdateEntityResult() {
        // Possibly for serialization.
    }

    public UpdateEntityResult(EntityDto result) {
        this.result = result;
    }

    public EntityDto getResult() {
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UpdateEntityResult other = (UpdateEntityResult) obj;
        if (result == null) {
            if (other.result != null)
                return false;
        } else if (!result.equals(other.result))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 23;
        hashCode = (hashCode * 37) + (result == null ? 1 : result.hashCode());
        return hashCode;
    }

    @Override
    public String toString() {
        return "UpdateEntityResult[" + result + "]";
    }
}
