package com.arcbees.gaestudio.client.application.visualizer.widget;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import java.util.List;

public class KindListView extends ViewWithUiHandlers<KindListUiHandlers> implements KindListPresenter.MyView,
        ChangeHandler {

    public interface Binder extends UiBinder<Widget, KindListView> {
    }

    @UiField
    ListBox kinds;

    @Inject
    public KindListView(final Binder uiBinder, final UiHandlersStrategy<KindListUiHandlers> uiHandlersStrategy) {
        super(uiHandlersStrategy);

        initWidget(uiBinder.createAndBindUi(this));

        kinds.addChangeHandler(this);
        kinds.addItem("Choose an entity...", "");
    }

    @Override
    public void onChange(ChangeEvent event) {
        String selectedKind = kinds.getValue(kinds.getSelectedIndex());

        getUiHandlers().onKindClicked(selectedKind);
    }

    @Override
    public void updateKinds(List<String> kinds) {
        clearEntityList();

        for (String kind : kinds) {
            this.kinds.addItem(kind);
        }
    }

    private void clearEntityList() {
        // Remove all kind except the Choose an entity option
        for (int i = 1; i < kinds.getItemCount(); i++) {
            kinds.removeItem(i);
        }
    }

}
