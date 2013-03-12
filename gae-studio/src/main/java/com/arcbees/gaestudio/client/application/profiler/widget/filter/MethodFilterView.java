/*
 * Copyright 2013 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
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
    public interface Binder extends UiBinder<Widget, MethodFilterView> {
    }

    @UiField
    Tree methods;

    @UiField(provided = true)
    AppResources resources;

    @Inject
    public MethodFilterView(final Binder uiBinder, final AppResources resources) {
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
