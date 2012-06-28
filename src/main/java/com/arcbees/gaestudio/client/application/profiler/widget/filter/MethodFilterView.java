package com.arcbees.gaestudio.client.application.profiler.widget.filter;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.client.Resources;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

import java.util.Map;

public class MethodFilterView extends ViewWithUiHandlers<MethodFilterUiHandlers>
        implements MethodFilterPresenter.MyView {

    public interface Binder extends UiBinder<Widget, MethodFilterView> {
    }

    @UiField
    Tree methods;

    @UiField(provided = true)
    Resources resources;

    @Inject
    public MethodFilterView(final Binder uiBinder, final Resources resources,
                            final UiHandlersStrategy<MethodFilterUiHandlers> uiHandlersStrategy) {
        super(uiHandlersStrategy);

        this.resources = resources;
        initWidget(uiBinder.createAndBindUi(this));

        methods.addSelectionHandler(new SelectionHandler<TreeItem>() {
            @Override
            public void onSelection(SelectionEvent<TreeItem> event) {
                TreeItem selectedItem = event.getSelectedItem();

                if (isLeaf(selectedItem)) {
                    String className = getClassName(selectedItem);
                    String methodName = selectedItem.getText();

                    getUiHandlers().onMethodClicked(className, methodName);
                }
            }
        });
    }

    @Override
    public void display(Map<String, Map<String, FilterValue<String>>> statementsByMethodAndClass) {
        methods.clear();

        for (Map.Entry<String, Map<String, FilterValue<String>>> classFilter : statementsByMethodAndClass.entrySet()) {
            TreeItem classTreeItem = new TreeItem(classFilter.getKey());

            for (String methodName : classFilter.getValue().keySet()) {
                TreeItem methodTreeItem = new TreeItem(methodName);
                classTreeItem.addItem(methodTreeItem);
            }

            methods.addItem(classTreeItem);
        }
    }

    private boolean isLeaf(TreeItem treeItem) {
        return (treeItem.getParentItem() != null);
    }

    private String getClassName(TreeItem treeItem) {
        TreeItem parentItem = treeItem.getParentItem();

        return parentItem.getText();
    }

}
