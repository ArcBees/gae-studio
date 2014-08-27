/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.cellview.client.CellTable;

import static com.google.gwt.query.client.GQuery.$;

public class CustomCellTable<T> extends CellTable<T> {
    private boolean shouldHide;

    public CustomCellTable(int pageSize, Resources resources) {
        super(pageSize, resources);
    }

    @Override
    protected void replaceAllChildren(List<T> values, SafeHtml html) {
        super.replaceAllChildren(values, html);

        updateColumnVisibility();
    }

    public void setShouldHide(boolean shouldHide) {
        this.shouldHide = shouldHide;

        updateColumnVisibility();
    }

    private void updateColumnVisibility() {
        GQuery.console.info("Called: " + shouldHide);

        if (shouldHide) {
            $("td:nth-child(2)", this).hide();
            $("th:nth-child(2)", this).hide();
        } else {
            $("td:nth-child(2)", this).show();
            $("th:nth-child(2)", this).show();
        }
    }

    public List<String> getHeaderNames() {
        NodeList<TableCellElement> cells = getTableHeadElement().getRows().getItem(0).getCells();

        List<String> names = Lists.newArrayList();

        for (int i = 0; i < cells.getLength(); i++) {
            names.add(cells.getItem(i).getInnerText());
        }

        return names;
    }
}
