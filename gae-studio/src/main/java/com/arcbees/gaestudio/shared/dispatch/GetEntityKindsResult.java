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

import java.util.ArrayList;

import com.gwtplatform.dispatch.shared.Result;

public class GetEntityKindsResult implements Result {
    private ArrayList<java.lang.String> kinds;

    protected GetEntityKindsResult() {
        // Possibly for serialization.
    }

    public GetEntityKindsResult(ArrayList<java.lang.String> kinds) {
        this.kinds = kinds;
    }

    public ArrayList<java.lang.String> getKinds() {
        return kinds;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GetEntityKindsResult other = (GetEntityKindsResult) obj;
        if (kinds == null) {
            if (other.kinds != null)
                return false;
        } else if (!kinds.equals(other.kinds))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 23;
        hashCode = (hashCode * 37) + (kinds == null ? 1 : kinds.hashCode());
        return hashCode;
    }

    @Override
    public String toString() {
        return "GetEntityKindsResult[" + kinds + "]";
    }
}
