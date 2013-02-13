package com.arcbees.gaestudio.client.application.visualizer.widget;

import java.util.List;

import com.arcbees.gaestudio.client.MyConstants;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class KindListView extends ViewWithUiHandlers<KindListUiHandlers> implements KindListPresenter.MyView,
        ChangeHandler {

    public interface Binder extends UiBinder<Widget, KindListView> {
    }

    @UiField
    ListBox kinds;

    private final MyConstants myConstants;

    @Inject
    public KindListView(final Binder uiBinder, final MyConstants myConstants) {
        this.myConstants = myConstants;

        initWidget(uiBinder.createAndBindUi(this));

        kinds.addChangeHandler(this);
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
        kinds.clear();
        kinds.addItem(myConstants.chooseAnEntity(), "");
    }

}
