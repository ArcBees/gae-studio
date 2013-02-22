package com.arcbees.gaestudio.shared.dispatch;

import com.gwtplatform.dispatch.shared.Action;

public class GetEmptyKindEntityAction implements Action<GetEmptyKindEntityResult> {
    private String kind;

    protected GetEmptyKindEntityAction() {
        // Possibly for serialization.
    }

    public GetEmptyKindEntityAction(String kind) {
        this.kind = kind;
    }

    @Override
    public String getServiceName() {
        return Action.DEFAULT_SERVICE_NAME + "GetEmptyKindEntity";
    }

    @Override
    public boolean isSecured() {
        return false;
    }

    public String getKind() {
        return kind;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GetEmptyKindEntityAction other = (GetEmptyKindEntityAction) obj;
        if (kind == null) {
            if (other.kind != null)
                return false;
        } else if (!kind.equals(other.kind))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 23;
        hashCode = (hashCode * 37) + (kind == null ? 1 : kind.hashCode());
        return hashCode;
    }

    @Override
    public String toString() {
        return "GetEmptyKindEntityAction[" + kind + "]";
    }
}
