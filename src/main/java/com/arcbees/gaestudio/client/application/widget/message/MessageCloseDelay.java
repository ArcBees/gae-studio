package com.arcbees.gaestudio.client.application.widget.message;

public enum MessageCloseDelay {

    NEVER(0),
    DEFAULT(5000),
    LONG(20000);

    private int delay;

    private MessageCloseDelay(int delay) {
        this.delay = delay;
    }

    public int getDelay() {
        return delay;
    }

}
