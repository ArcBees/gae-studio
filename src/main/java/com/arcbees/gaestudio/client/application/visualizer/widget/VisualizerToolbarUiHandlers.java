/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import com.gwtplatform.mvp.client.UiHandlers;

public interface VisualizerToolbarUiHandlers extends UiHandlers {
    void refresh();

    void create();

    void delete();

    void edit();
}
