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

import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
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
