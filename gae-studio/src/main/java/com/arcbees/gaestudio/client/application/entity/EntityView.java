package com.arcbees.gaestudio.client.application.entity;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.gwtplatform.mvp.client.ViewImpl;

public class EntityView extends ViewImpl implements EntityPresenter.MyView {
    interface Binder extends UiBinder<HTMLPanel, EntityView> {
    }

    @UiField
    Label textLabel;

    @Inject
    public EntityView(Binder binder) {
        initWidget(binder.createAndBindUi(this));
    }

    @Override
    public void showEntity(String json) {
        textLabel.setText(json);
    }
}
