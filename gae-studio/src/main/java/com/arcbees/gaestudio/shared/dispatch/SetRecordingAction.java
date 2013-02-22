package com.arcbees.gaestudio.shared.dispatch;

import com.arcbees.gaestudio.shared.dispatch.util.GaeStudioActionImpl;

public class SetRecordingAction extends GaeStudioActionImpl<SetRecordingResult> {
    private boolean starting;

    protected SetRecordingAction() {
        // Possibly for serialization.
    }

    public SetRecordingAction(boolean starting) {
        this.starting = starting;
    }

    public boolean isStarting() {
        return starting;
    }
}
