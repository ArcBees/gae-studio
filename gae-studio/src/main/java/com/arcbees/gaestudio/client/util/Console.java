package com.arcbees.gaestudio.client.util;

public class Console {
    public static native void log(String message)/*-{
        $wnd.console.log(message)
    }-*/;
}
