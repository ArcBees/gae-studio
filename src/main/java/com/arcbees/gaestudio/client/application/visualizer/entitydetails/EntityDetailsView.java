package com.arcbees.gaestudio.client.application.visualizer.entitydetails;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.shared.dto.entity.EntityDTO;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class EntityDetailsView extends ViewWithUiHandlers<EntityDetailsUiHandlers>
        implements EntityDetailsPresenter.MyView {

    public interface Binder extends UiBinder<Widget, EntityDetailsView> {
    }

    @UiField
    TextArea entityDetails;
    @UiField
    Button edit;

    private EntityDTO currentEntityDTO;

    @Inject
    public EntityDetailsView(final Binder uiBinder,
                             final UiHandlersStrategy<EntityDetailsUiHandlers> uiHandlersStrategy) {
        super(uiHandlersStrategy);
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void displayEntityDetails(EntityDTO entityDTO) {
        currentEntityDTO = entityDTO;
        entityDetails.setText(entityDTO.getJson());
    }

    @UiHandler("edit")
    void onEditClicked(ClickEvent event){
        currentEntityDTO.setJson(entityDetails.getValue());
        getUiHandlers().editEntity(currentEntityDTO);
    }

}
