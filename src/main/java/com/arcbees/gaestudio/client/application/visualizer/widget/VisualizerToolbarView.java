package com.arcbees.gaestudio.client.application.visualizer.widget;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.client.Resources;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class VisualizerToolbarView extends ViewWithUiHandlers<VisualizerToolbarUiHandlers> implements
        VisualizerToolbarPresenter.MyView {

    public interface Binder extends UiBinder<Widget, VisualizerToolbarView> {
    }

    @UiField(provided = true)
    Resources resources;
    @UiField
    SimplePanel kinds;
    @UiField
    HTMLPanel buttons;
    @UiField
    Button refresh;
    @UiField
    Button create;
    @UiField
    Button edit;
    @UiField
    Button delete;

    @Inject
    public VisualizerToolbarView(final Binder uiBinder, final Resources resources,
                                 final UiHandlersStrategy<VisualizerToolbarUiHandlers> uiHandlersStrategy) {
        super(uiHandlersStrategy);

        this.resources = resources;
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setKindSelected(boolean isSelected) {
        buttons.setVisible(isSelected);
    }

    @Override
    public void setInSlot(Object slot, Widget content) {
        if (slot == VisualizerToolbarPresenter.TYPE_SetKindsContent) {
            kinds.setWidget(content);
        }
    }

    @Override
    public void enableContextualMenu() {
        edit.setEnabled(true);
        delete.setEnabled(true);
    }

    @Override
    public void disableContextualMenu() {
        edit.setEnabled(false);
        delete.setEnabled(false);
    }

    @UiHandler("refresh")
    void onRefreshClicked(ClickEvent event) {
        getUiHandlers().refresh();
    }

    @UiHandler("create")
    void onCreateClicked(ClickEvent event){
        getUiHandlers().create();
    }

    @UiHandler("edit")
    void onEditClicked(ClickEvent event){
       getUiHandlers().edit();
    }

    @UiHandler("delete")
    void onDeleteClicked(ClickEvent event){
       getUiHandlers().delete();
    }
}
