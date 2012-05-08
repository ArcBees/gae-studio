package com.arcbees.gaestudio.client.application.visualizer.entitylist;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.shared.dto.entity.Entity;
import com.arcbees.gaestudio.shared.dto.entity.Key;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.CssResource;
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

    public interface MyStyle extends CssResource {
        String entityList();
        String entity();
    }
    
    @UiField
    MyStyle style;
    
    @UiField
    HTMLPanel entityList;
    
    @Inject
    public EntityListView(final Binder uiBinder, final UiHandlersStrategy<EntityListUiHandlers> uiHandlersStrategy) {
        super(uiHandlersStrategy);
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
            StringBuilder builder = new StringBuilder();
            
            builder.append("[Kind: ");
            builder.append(entity.getKey().getKind());
            builder.append("] [Id: ");
            builder.append(entity.getKey().getId());
            builder.append("] [");
            builder.append(entity.toString());
            builder.append("]");
            
            entityList.add(createEntityElement(builder.toString(), entity.getKey(), entity.getJson()));
        }
    }

    private HTML createEntityElement(String name, final Key entityKey, final String entityData) {
        HTML html = new HTML(name);

        html.setStyleName(style.entity());
        html.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                getUiHandlers().onEntityClicked(entityKey, entityData);
            }
        });

        return html;
    }

}
