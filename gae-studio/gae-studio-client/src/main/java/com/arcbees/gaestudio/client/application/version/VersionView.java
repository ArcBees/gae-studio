/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.version;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class VersionView extends ViewImpl implements VersionPresenter.MyView {
    interface Binder extends UiBinder<Widget, VersionView> {
    }

    @UiField
    Label label;

    @Inject
    VersionView(Binder binder) {
        initWidget(binder.createAndBindUi(this));
    }

    @Override
    public void setVersion(String version) {
        label.setText(version);
    }
}
