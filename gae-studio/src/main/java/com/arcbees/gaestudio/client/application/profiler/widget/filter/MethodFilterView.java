/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.profiler.widget.filter;

import java.util.Map;

import com.arcbees.gaestudio.client.resources.AppResources;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class MethodFilterView extends ViewWithUiHandlers<MethodFilterUiHandlers>
        implements MethodFilterPresenter.MyView {
    interface Binder extends UiBinder<Widget, MethodFilterView> {
    }

    @UiField
    Tree methods;

    @UiField(provided = true)
    AppResources resources;

    @Inject
    MethodFilterView(Binder uiBinder,
                     AppResources resources) {
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
            // TODO TreeItem deprecated
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
