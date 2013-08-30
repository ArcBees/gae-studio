/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.error;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class LicenseExpiredView extends ViewImpl implements LicenseExpiredPresenter.MyView {
    interface Binder extends UiBinder<Widget, LicenseExpiredView> {
    }

    @Inject
    LicenseExpiredView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }
}
