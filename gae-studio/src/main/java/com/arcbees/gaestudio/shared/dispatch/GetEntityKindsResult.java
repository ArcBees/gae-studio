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
