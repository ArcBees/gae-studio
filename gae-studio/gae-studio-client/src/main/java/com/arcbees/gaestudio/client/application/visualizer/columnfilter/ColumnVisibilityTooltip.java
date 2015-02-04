/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.columnfilter;

import javax.inject.Inject;

import com.arcbees.gquery.tooltip.client.Tooltip;
import com.arcbees.gquery.tooltip.client.TooltipOptions;
import com.arcbees.gquery.tooltip.client.TooltipResources;
import com.google.gwt.dom.client.Document;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;

import static com.google.gwt.query.client.GQuery.$;

public class ColumnVisibilityTooltip {
    private static final String HIDE_DOM_EVENT = "mouseleave";

    private final TooltipResources tooltipResources;

    private Tooltip tooltip;

    @Inject
    ColumnVisibilityTooltip(TooltipResources tooltipResources) {
        this.tooltipResources = tooltipResources;
    }

    public void bind(GQuery anchor, String eventType, final IsWidget content) {
        $(Document.get()).click(new Function() {
            @Override
            public boolean f(Event e) {
                if (tooltip != null) {
                    if (isClickEventFromOutsideTooltip(e)) {
                        tooltip.hide();
                        $("." + tooltipResources.css().tooltip()).unbind(HIDE_DOM_EVENT);
                    }
                }

                return true;
            }
        });

        anchor.bind(eventType, new Function() {
            @Override
            public boolean f(Event e) {
                e.preventDefault();

                if (tooltip != null) {
                    tooltip.destroy();
                }

                int left = e.getClientX() - Window.getClientWidth() / 2;
                int top = e.getClientY() - Window.getClientHeight();

                TooltipOptions tooltipOptions = buildTooltipOptions(left, top, content);

                tooltip = $("body").as(Tooltip.Tooltip).tooltip(tooltipOptions);
                tooltip.show();

                bindAutoHide();

                return false;
            }
        });
    }

    private TooltipOptions buildTooltipOptions(int left, int top, IsWidget content) {
        TooltipOptions tooltipOptions = new TooltipOptions();

        tooltipOptions.withContent(content);
        tooltipOptions.withTrigger(TooltipOptions.TooltipTrigger.MANUAL);
        tooltipOptions.withPlacement(TooltipOptions.TooltipPlacement.BOTTOM);
        tooltipOptions.withOffset(new GQuery.Offset(left, top));

        return tooltipOptions;
    }

    private boolean isClickEventFromOutsideTooltip(Event e) {
        return $(e.getEventTarget()).closest("." + tooltipResources.css().tooltip()).length() == 0;
    }

    private void bindAutoHide() {
        $("." + tooltipResources.css().tooltip()).bind(HIDE_DOM_EVENT, new Function() {
            @Override
            public boolean f(Event e) {
                tooltip.hide();

                return true;
            }
        });
    }
}
