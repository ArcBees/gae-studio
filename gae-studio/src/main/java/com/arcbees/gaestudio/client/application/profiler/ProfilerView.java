package com.arcbees.gaestudio.client.application.profiler;

import com.arcbees.core.client.mvp.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import java.util.List;

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

    @Inject
    public ProfilerView(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

}
    