/**
 * Copyright 2012 ArcBees Inc.  All rights reserved.
 */

package com.arcbees.gaestudio.client.application.header;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class HeaderPresenter extends PresenterWidget<HeaderPresenter.MyView> {

    public interface MyView extends View {
    }

    @Inject
    public HeaderPresenter(final EventBus eventBus, final MyView view) {
        super(eventBus, view);
    }

}
