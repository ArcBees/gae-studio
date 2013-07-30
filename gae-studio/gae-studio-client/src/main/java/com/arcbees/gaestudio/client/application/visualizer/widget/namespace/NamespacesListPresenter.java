/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget.namespace;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

public class NamespacesListPresenter extends PresenterWidget<NamespacesListPresenter.MyView> implements
        NamespacesListUiHandlers {
    interface MyView extends View, HasUiHandlers<NamespacesListUiHandlers> {
    }

    @Inject
    NamespacesListPresenter(EventBus eventBus,
                            MyView view) {
        super(eventBus, view);

        getView().setUiHandlers(this);
    }

    @Override
    protected void onBind() {
        super.onBind();
    }
}
