package com.arcbees.gaestudio.client.application.header;

import com.arcbees.core.client.mvp.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class HeaderView extends ViewImpl implements HeaderPresenter.MyView {

    public interface Binder extends UiBinder<Widget, HeaderView> {
    }

    @Inject
    public HeaderView(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

}
    