package com.arcbees.gaestudio.client.application.profiler.widget.filter;

import com.arcbees.core.client.mvp.ViewImpl;
import com.arcbees.gaestudio.client.Resources;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class FiltersView extends ViewImpl implements FiltersPresenter.MyView {

    public interface Binder extends UiBinder<Widget, FiltersView> {
    }

    @UiField(provided = true)
    Resources resources;

    @UiField
    SimplePanel request;

    @UiField
    SimplePanel method;

    @Inject
    public FiltersView(final Binder uiBinder, final Resources resources) {
        this.resources = resources;

        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setInSlot(Object slot, Widget content) {
        if (slot == FiltersPresenter.TYPE_SetRequestFilter) {
            request.setWidget(content);
        } else if (slot == FiltersPresenter.TYPE_ClassMethodFilter) {
            method.setWidget(content);
        }
    }

}
