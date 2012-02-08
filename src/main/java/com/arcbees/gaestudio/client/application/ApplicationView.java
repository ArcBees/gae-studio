package com.arcbees.gaestudio.client.application;

import com.arcbees.core.client.mvp.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

import javax.inject.Inject;

public class ApplicationView extends ViewImpl implements ApplicationPresenter.MyView {

    public interface Binder extends UiBinder<Widget, ApplicationView> {
    }
    
    @UiField
    SimplePanel header;

    @UiField
    SimpleLayoutPanel main;

    @Inject
    public ApplicationView(final Binder uiBinder) {
       initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setInSlot(Object slot, Widget content) {
        if (content != null) {
            if (slot == ApplicationPresenter.TYPE_SetMainContent) {
                main.setWidget(content);
            } else if (slot == ApplicationPresenter.TYPE_SetHeaderContent) {
                header.setWidget(content);
            }
        }
    }
}
