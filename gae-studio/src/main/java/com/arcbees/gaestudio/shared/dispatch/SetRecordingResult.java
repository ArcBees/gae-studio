/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.shared.dispatch;

import com.gwtplatform.dispatch.shared.Result;

public class SetRecordingResult implements Result {

    Long currentRecordId;

    protected SetRecordingResult() {
        // Possibly for serialization.
    }

    public SetRecordingResult(Long currentRecordId) {
        this.currentRecordId = currentRecordId;
    }

    public Long getCurrentRecordId() {
        return currentRecordId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SetRecordingResult other = (SetRecordingResult) obj;
        if (currentRecordId == null) {
            if (other.currentRecordId != null)
                return false;
        } else if (!currentRecordId.equals(other.currentRecordId))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 23;
        hashCode = (hashCode * 37) + (currentRecordId == null ? 1 : currentRecordId.hashCode());
        return hashCode;
    }

    @Override
    public String toString() {
        return "SetRecordingResult[" + currentRecordId + "]";
    }
}
