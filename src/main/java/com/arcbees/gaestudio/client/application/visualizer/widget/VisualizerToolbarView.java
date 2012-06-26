package com.arcbees.gaestudio.client.application.visualizer.widget;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.client.Resources;
import com.arcbees.gaestudio.client.application.ui.ToolbarButton;
import com.arcbees.gaestudio.client.application.ui.ToolbarButtonCallback;
import com.arcbees.gaestudio.client.application.ui.UiFactory;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
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
    private final UiFactory uiFactory;
    @UiField
    SimplePanel kinds;
    @UiField
    HTMLPanel buttons;

    private final ToolbarButton refresh;
    private final ToolbarButton create;
    private final ToolbarButton edit;
    private final ToolbarButton delete;

    @Inject
    public VisualizerToolbarView(final Binder uiBinder, final Resources resources, final UiFactory uiFactory,
                                 final UiHandlersStrategy<VisualizerToolbarUiHandlers> uiHandlersStrategy) {
        super(uiHandlersStrategy);

        this.resources = resources;
        this.uiFactory = uiFactory;
        initWidget(uiBinder.createAndBindUi(this));

        refresh = createRefreshButton();
        create = createCreateButton();
        edit = createEditButton();
        delete = createDeleteButton();

        buttons.add(refresh);
        buttons.add(create);
        buttons.add(edit);
        buttons.add(delete);

        edit.setEnabled(false);
        delete.setEnabled(false);
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


    private ToolbarButton createRefreshButton() {
        return uiFactory.createToolbarButton("Refresh", resources.refresh(),
                new ToolbarButtonCallback() {
                    @Override
                    public void onClicked() {
                        getUiHandlers().refresh();
                    }
                });
    }


    private ToolbarButton createCreateButton() {
        return uiFactory.createToolbarButton("Create", resources.create(),
                new ToolbarButtonCallback() {
                    @Override
                    public void onClicked() {
                        getUiHandlers().create();
                    }
                });
    }


    private ToolbarButton createEditButton() {
        return uiFactory.createToolbarButton("Edit", resources.edit(),
                new ToolbarButtonCallback() {
                    @Override
                    public void onClicked() {
                        getUiHandlers().edit();
                    }
                });
    }


    private ToolbarButton createDeleteButton() {
        return uiFactory.createToolbarButton("Delete", resources.delete(),
                new ToolbarButtonCallback() {
                    @Override
                    public void onClicked() {
                        getUiHandlers().delete();
                    }
                });
    }

}
