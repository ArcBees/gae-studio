package com.arcbees.gaestudio.client.application.profiler;

import com.arcbees.core.client.mvp.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class StatisticsView extends ViewImpl implements StatisticsPresenter.MyView {

    public interface Binder extends UiBinder<Widget, StatisticsView> {
    }

    @Inject
    public StatisticsView(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

}
    