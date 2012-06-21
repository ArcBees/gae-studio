package com.arcbees.gaestudio.client.application.profiler.widget;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.client.Resources;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class ProfilerToolbarView extends ViewWithUiHandlers<ProfilerToolbarUiHandlers> implements
        ProfilerToolbarPresenter.MyView {

    public interface Binder extends UiBinder<Widget, ProfilerToolbarView> {
    }

    @UiField(provided = true)
    Resources resources;
    @UiField
    Button record;
    @UiField
    Button stop;
    @UiField
    Button clear;

    @Inject
    public ProfilerToolbarView(final Binder uiBinder, final Resources resources,
                               final UiHandlersStrategy<ProfilerToolbarUiHandlers> uiHandlersStrategy) {
        super(uiHandlersStrategy);

        this.resources = resources;
        initWidget(uiBinder.createAndBindUi(this));
    }

}
