package com.arcbees.gaestudio.client.application.entitykindlist;

import com.arcbees.core.client.mvp.ViewImpl;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import java.util.List;

public class EntityKindListView extends ViewImpl implements EntityKindListPresenter.MyView {

    public interface Binder extends UiBinder<Widget, EntityKindListView> {
    }
    
    @UiField
    SimplePanel entityKinds;

    @Inject
    public EntityKindListView(final Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setEntityKinds(List<String> entityKinds) {
        entityKinds.clear();
        for (String kind : entityKinds) {
            entityKinds.add(kind);
            entityKinds.add("<br/>");
        }
    }

}
    