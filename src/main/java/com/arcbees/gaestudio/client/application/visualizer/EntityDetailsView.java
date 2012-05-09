package com.arcbees.gaestudio.client.application.visualizer;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.shared.dto.entity.KeyDTO;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class EntityDetailsView extends ViewWithUiHandlers<EntityDetailsUiHandlers>
        implements EntityDetailsPresenter.MyView {

    public interface Binder extends UiBinder<Widget, EntityDetailsView> {
    }

    @UiField
    HTML entityDetails;

    @Inject
    public EntityDetailsView(final Binder uiBinder,
                             final UiHandlersStrategy<EntityDetailsUiHandlers> uiHandlersStrategy) {
        super(uiHandlersStrategy);
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void displayEntityDetails(KeyDTO entityKey, String entityData) {
        entityDetails.setHTML(entityData);
    }

}
    