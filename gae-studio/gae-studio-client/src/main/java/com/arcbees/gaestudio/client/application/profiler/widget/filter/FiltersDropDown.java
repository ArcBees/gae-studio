/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.profiler.widget.filter;

import java.util.List;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.resources.AppResources;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;

import static com.google.gwt.query.client.GQuery.$;

public class FiltersDropDown implements IsWidget, AttachEvent.Handler {
    public interface FilterSelectedHandler {
        void onFilterSelected(Filter filter);
    }

    interface Binder extends UiBinder<Widget, FiltersDropDown> {
    }

    private final Widget widget;
    private final Element list;
    private final AppResources resources;
    private final FilterSelectedHandler handler;

    private boolean opened;

    @Inject
    FiltersDropDown(AppResources resources,
                    Binder binder,
                    @Assisted FilterSelectedHandler handler) {
        this.resources = resources;
        this.handler = handler;

        widget = binder.createAndBindUi(this);

        widget.addAttachHandler(this);
        list = widget.getElement();
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    @Override
    public void onAttachOrDetach(AttachEvent event) {
        if (event.isAttached()) {
            init();
        } else {
            clear();
        }
    }

    private void clear() {
        list().unbind(Event.ONBLUR);
        everyLi().unbind(Event.ONCLICK);
    }

    private void init() {
        list().blur(new Function() {
            @Override
            public boolean f(Event e) {
                GQuery element = $("li." + resources.styles().selectedLi());
                close(element);

                return true;
            }
        });

        everyLi().click(new Function() {
            @Override
            public boolean f(Event e) {
                GQuery li = $(getElement());

                if (li.hasClass(resources.styles().selectedLi())) {
                    if (opened) {
                        close(li);
                    } else {
                        open();
                    }
                } else {
                    close(li);
                }

                return true;
            }
        });
    }

    private void open() {
        list().addClass(resources.styles().openedList());
        everyLi().removeClass(resources.styles().hiddenLi(), resources.styles().dropDownArrow());
        $("li:first-child", list).addClass(resources.styles().dropDownArrow());

        opened = true;
    }

    private void close(GQuery li) {
        list().removeClass(resources.styles().openedList());

        everyLi().addClass(resources.styles().hiddenLi());
        everyLi().removeClass(resources.styles().selectedLi(), resources.styles().dropDownArrow());

        $(li).removeClass(resources.styles().hiddenLi());
        $(li).addClass(resources.styles().selectedLi(), resources.styles().dropDownArrow());

        Filter filter = parseFilter(li);
        handler.onFilterSelected(filter);

        opened = false;
    }

    private GQuery list() {
        return $(list);
    }

    private GQuery everyLi() {
        return $("li", list);
    }

    private Filter parseFilter(GQuery li) {
        String text = $(li).text().toUpperCase();

        List<String> tokens = Splitter.on(" ").trimResults().omitEmptyStrings().splitToList(text);
        String type = Iterables.getLast(tokens);

        return Filter.valueOf(type);
    }
}
