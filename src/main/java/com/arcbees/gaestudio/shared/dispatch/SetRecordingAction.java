package com.arcbees.gaestudio.shared.dispatch;

import com.gwtplatform.dispatch.shared.Action;

public class SetRecordingAction implements Action<SetRecordingResult> {
    private boolean starting;

    protected SetRecordingAction() {
        // Possibly for serialization.
    }

    public SetRecordingAction(boolean starting) {
        this.starting = starting;
    }

    @Override
    public String getServiceName() {
        return Action.DEFAULT_SERVICE_NAME + "SetRecording";
    }

    @Override
    public boolean isSecured() {
        return false;
    }

    public boolean isStarting() {
        return starting;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SetRecordingAction other = (SetRecordingAction) obj;
        if (starting != other.starting)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 23;
        hashCode = (hashCode * 37) + new Boolean(starting).hashCode();
        return hashCode;
    }

    @Override
    public String toString() {
        return "SetRecordingAction[" + starting + "]";
    }
}
