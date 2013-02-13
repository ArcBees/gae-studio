package com.arcbees.gaestudio.client.application.visualizer;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class VisualizerView extends ViewImpl implements VisualizerPresenter.MyView {

    public interface Binder extends UiBinder<Widget, VisualizerView> {
    }

    @UiField
    SimplePanel entityListPanel;

    @UiField
    SimplePanel toolbar;

    @Inject
    public VisualizerView(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setInSlot(Object slot, Widget content) {
        if (content != null) {
            if (slot == VisualizerPresenter.TYPE_SetEntityListPanelContent) {
                entityListPanel.setWidget(content);
            } else if (slot == VisualizerPresenter.TYPE_SetToolbarContent) {
                toolbar.setWidget(content);
            }
        }
    }

}
