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