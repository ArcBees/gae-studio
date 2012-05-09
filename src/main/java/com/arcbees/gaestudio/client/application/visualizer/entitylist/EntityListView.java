package com.arcbees.gaestudio.client.application.visualizer.entitylist;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.client.Resources;
import com.arcbees.gaestudio.client.application.ui.BaseLabel;
import com.arcbees.gaestudio.client.application.ui.LabelCallback;
import com.arcbees.gaestudio.client.application.visualizer.VisualizerLabelFactory;
import com.arcbees.gaestudio.shared.dto.entity.Entity;
import com.arcbees.gaestudio.shared.dto.entity.Key;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import java.util.ArrayList;

// TODO see if I can factor out some of the common logic in the kind and entity list views
public class EntityListView extends ViewWithUiHandlers<EntityListUiHandlers> implements EntityListPresenter.MyView {

    public interface Binder extends UiBinder<Widget, EntityListView> {
    }

    @UiField
    HTMLPanel entityList;

    @UiField(provided = true)
    Resources resources;

    private final VisualizerLabelFactory visualizerLabelFactory;
    private BaseLabel<Entity> selectedBaseLabel;

    @Inject
    public EntityListView(final Binder uiBinder, final UiHandlersStrategy<EntityListUiHandlers> uiHandlersStrategy,
                          final Resources resources, final VisualizerLabelFactory visualizerLabelFactory) {
        super(uiHandlersStrategy);
        this.resources = resources;
        this.visualizerLabelFactory = visualizerLabelFactory;
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void displayEntities(ArrayList<Entity> entities) {
        entityList.clear();

        if (entities == null) {
            return;
        }

        // TODO use a cell table
        for (Entity entity : entities) {
            entityList.add(createEntityElement(entity));
        }
    }

    private EntityLabel createEntityElement(final Entity entity){
        return visualizerLabelFactory.createEntity(entity, new LabelCallback<Entity>() {
            @Override
            public void onClick(BaseLabel baseLabel, Entity entity) {
                onEntityClicked(baseLabel, entity);
            }
        });
    }

    private void onEntityClicked(BaseLabel baseLabel, Entity entity) {
        getUiHandlers().onEntityClicked(entity.getKey(), entity.getJson());
        if (selectedBaseLabel != null) {
            selectedBaseLabel.setSelected(false);
        }
        selectedBaseLabel = baseLabel;
        baseLabel.setSelected(true);
    }

}
