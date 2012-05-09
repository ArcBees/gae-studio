package com.arcbees.gaestudio.client.application.visualizer.entitylist;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.client.Resources;
import com.arcbees.gaestudio.client.application.ui.BaseLabel;
import com.arcbees.gaestudio.client.application.ui.LabelCallback;
import com.arcbees.gaestudio.client.application.ui.SelectableLabelServant;
import com.arcbees.gaestudio.client.application.visualizer.VisualizerLabelFactory;
import com.arcbees.gaestudio.shared.dto.entity.EntityDTO;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import java.util.ArrayList;

public class EntityListView extends ViewWithUiHandlers<EntityListUiHandlers> implements EntityListPresenter.MyView {

    public interface Binder extends UiBinder<Widget, EntityListView> {
    }

    @UiField
    HTMLPanel entityList;

    @UiField(provided = true)
    Resources resources;

    private final VisualizerLabelFactory visualizerLabelFactory;
    private final SelectableLabelServant selectableLabelServant;

    @Inject
    public EntityListView(final Binder uiBinder, final UiHandlersStrategy<EntityListUiHandlers> uiHandlersStrategy,
                          final Resources resources, final VisualizerLabelFactory visualizerLabelFactory,
                          final SelectableLabelServant selectableLabelServant) {
        super(uiHandlersStrategy);
        this.resources = resources;
        this.visualizerLabelFactory = visualizerLabelFactory;
        this.selectableLabelServant = selectableLabelServant;
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void displayEntities(ArrayList<EntityDTO> entities) {
        entityList.clear();

        if (entities == null) {
            return;
        }

        // TODO use a cell table
        for (EntityDTO entity : entities) {
            entityList.add(createEntityElement(entity));
        }
    }

    private EntityLabel createEntityElement(final EntityDTO entity) {
        return visualizerLabelFactory.createEntity(entity, new LabelCallback() {
            @Override
            public void onClick(BaseLabel baseLabel) {
                selectableLabelServant.select(baseLabel);
                getUiHandlers().onEntityClicked(entity.getKey(), entity.getJson());
            }
        });
    }

}
