package com.arcbees.gaestudio.client.application.visualizer;

import com.arcbees.core.client.mvp.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class VisualizerView extends ViewImpl implements VisualizerPresenter.MyView {

    public interface Binder extends UiBinder<Widget, VisualizerView> {
    }

    @Inject
    public VisualizerView(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

}
    