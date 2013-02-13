package com.arcbees.gaestudio.shared.dispatch;

import com.gwtplatform.dispatch.shared.Action;

public class GetEntityKindsAction implements Action<GetEntityKindsResult> {
    public GetEntityKindsAction() {
        // Possibly for serialization.
    }

    @Override
    public String getServiceName() {
        return Action.DEFAULT_SERVICE_NAME + "GetEntityKinds";
    }

    @Override
    public boolean isSecured() {
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "GetEntityKindsAction[" + "]";
    }
}
