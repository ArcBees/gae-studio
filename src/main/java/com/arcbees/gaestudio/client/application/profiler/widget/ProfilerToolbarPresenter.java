/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler.widget;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class ProfilerToolbarPresenter extends PresenterWidget<ProfilerToolbarPresenter.MyView> implements
        ProfilerToolbarUiHandlers {

    public interface MyView extends View, HasUiHandlers<ProfilerToolbarUiHandlers> {
    }

    @Inject
    public ProfilerToolbarPresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);
    }

}

