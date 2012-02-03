package com.arcbees.gaestudio.client.application.profiler;

import com.arcbees.core.client.mvp.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class StatementView extends ViewImpl implements StatementPresenter.MyView {

    public interface Binder extends UiBinder<Widget, StatementView> {
    }

    @Inject
    public StatementView(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

}
    