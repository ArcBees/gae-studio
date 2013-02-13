package com.arcbees.gaestudio.client.application.profiler;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;

public class ProfilerView extends ViewImpl implements ProfilerPresenter.MyView {

    public interface Binder extends UiBinder<Widget, ProfilerView> {
    }

    @UiField
    SimplePanel requestPanel;
    @UiField
    SimplePanel statisticsPanel;
    @UiField
    SimplePanel statementPanel;
    @UiField
    SimplePanel detailsPanel;
    @UiField
    SimplePanel toolbarPanel;

    @Inject
    public ProfilerView(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setInSlot(Object slot, Widget content) {
        if (content != null) {
            if (slot == ProfilerPresenter.TYPE_SetRequestPanelContent) {
                requestPanel.setWidget(content);
            } else if (slot == ProfilerPresenter.TYPE_SetStatisticsPanelContent) {
                statisticsPanel.setWidget(content);
            } else if (slot == ProfilerPresenter.TYPE_SetStatementPanelContent) {
                statementPanel.setWidget(content);
            } else if (slot == ProfilerPresenter.TYPE_SetDetailsPanelContent) {
                detailsPanel.setWidget(content);
            } else if (slot == ProfilerPresenter.TYPE_SetToolbarContent) {
                toolbarPanel.setWidget(content);
            }
        }
    }

}
