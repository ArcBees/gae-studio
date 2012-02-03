package com.arcbees.gaestudio.client.application.profiler;

import com.arcbees.core.client.mvp.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class DetailsView extends ViewImpl implements DetailsPresenter.MyView {

    public interface Binder extends UiBinder<Widget, DetailsView> {
    }

    @Inject
    public DetailsView(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

}
    