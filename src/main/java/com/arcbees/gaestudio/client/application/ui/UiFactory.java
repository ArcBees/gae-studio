package com.arcbees.gaestudio.client.application.ui;

import com.google.gwt.resources.client.ImageResource;

public interface UiFactory {

    public ToolbarButton createToolbarButton(final String text, final ImageResource imageResource,
                                             final ToolbarButtonCallback callback);

}
