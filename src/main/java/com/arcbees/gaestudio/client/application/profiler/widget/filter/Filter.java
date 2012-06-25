package com.arcbees.gaestudio.client.application.profiler.widget.filter;

public enum Filter {

    REQUEST("by Request"),
    METHOD("by Method");

    private String text;

    private Filter(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
