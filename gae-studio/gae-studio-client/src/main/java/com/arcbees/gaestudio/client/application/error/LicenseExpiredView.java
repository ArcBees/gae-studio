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
