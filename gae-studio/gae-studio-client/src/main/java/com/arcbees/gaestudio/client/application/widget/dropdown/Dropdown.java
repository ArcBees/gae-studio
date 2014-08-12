/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.widget.dropdown;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.ObjectUtils;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.query.client.Function;
import com.google.gwt.query.client.GQuery;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.GssResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.query.client.GQuery.$;

public class Dropdown<T> implements IsWidget, AttachEvent.Handler, HasValueChangeHandlers<T> {
    public interface Resources extends ClientBundle {
        ImageResource dropDownArrowUp();

        ImageResource dropDownArrowRl();

        Style styles();
    }

    public interface Style extends GssResource {
        String selectedLi();

        String dropDownArrow();

        String hiddenLi();

        String openedList();

        String ul();
    }

    private final Widget widget;
    private final Element list;
    private final Map<Element, T> valuesIndex;
    private final Renderer<T> renderer;
    private final EventBus eventBus;
    private final Resources resources;

    private boolean opened;

    Dropdown(Renderer<T> renderer, Resources resources) {
        this.renderer = renderer;
        this.eventBus = new SimpleEventBus();
        this.resources = resources;

        resources.styles().ensureInjected();

        widget = new HTMLPanel("ul", "");
        widget.setStyleName(resources.styles().ul());
        widget.addAttachHandler(this);

        list = widget.getElement();

        valuesIndex = new HashMap<>();
    }

    public void clear() {
        clearChildrenBindings();
        valuesIndex.clear();
        list().children().remove();
    }

    public void addValue(T value) {
        Element elem = Document.get().createLIElement();

        if (valuesIndex.isEmpty()) {
            elem.addClassName(resources.styles().selectedLi());
            elem.addClassName(resources.styles().dropDownArrow());
        } else {
            elem.addClassName(resources.styles().hiddenLi());
        }

        elem.setInnerSafeHtml(SafeHtmlUtils.fromTrustedString(renderer.render(value)));
        valuesIndex.put(elem, value);
        $(list).append(elem);

        $(elem).unbind(Event.ONCLICK).click(clickhandler);
    }

    public void addValues(List<T> values) {
        for (T value : values) {
            addValue(value);
        }
    }

    public void setValue(T value) {
        setValue(value, true);
    }

    public void setValue(T value, boolean fireEvent) {
        for (Map.Entry<Element, T> valueEntry : valuesIndex.entrySet()) {
            T entryValue = valueEntry.getValue();
            if (entryValue == null && value == null || entryValue != null && entryValue.equals(value)) {
                makeSelected($(valueEntry.getKey()), fireEvent);
                return;
            }
        }
    }

    private Function clickhandler = new Function() {
        @Override
        public boolean f(Event e) {
            GQuery li = $(getElement());

            if (li.hasClass(resources.styles().selectedLi())) {
                if (opened) {
                    close();
                    makeSelected(li, true);
                } else {
                    open();
                }
            } else {
                close();
                makeSelected(li, true);
            }

            return true;
        }
    };

    @Override
    public void fireEvent(GwtEvent<?> event) {
        eventBus.fireEvent(event);
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<T> handler) {
        return eventBus.addHandler(ValueChangeEvent.getType(), handler);
    }

    public T getValue() {
        Element element = $("li." + resources.styles().selectedLi(), list).get(0);
        return valuesIndex.get(element);
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
            clearBindings();
        }
    }

    private void init() {
        list().blur(new Function() {
            @Override
            public boolean f(Event e) {
                GQuery selected = $("li." + resources.styles().selectedLi());
                close();
                makeSelected(selected, false);

                return true;
            }
        });


        clearChildrenBindings();
    }

    private void clearChildrenBindings() {
        for (Element element : valuesIndex.keySet()) {
            $(element).unbind(Event.ONCLICK).click(clickhandler);
        }
    }

    private void close() {
        list().removeClass(resources.styles().openedList());

        everyLi().addClass(resources.styles().hiddenLi());
        everyLi().removeClass(resources.styles().selectedLi(), resources.styles().dropDownArrow());

        opened = false;
    }

    private void makeSelected(GQuery selectedLi, boolean fireEvent) {
        $("." + resources.styles().selectedLi(), widget)
                .removeClass(resources.styles().selectedLi(), resources.styles().dropDownArrow())
                .addClass(resources.styles().hiddenLi());
        $(selectedLi).removeClass(resources.styles().hiddenLi());
        $(selectedLi).addClass(resources.styles().selectedLi(), resources.styles().dropDownArrow());

        Element element = $(selectedLi).get(0);
        T value = valuesIndex.get(element);

        if (fireEvent) {
            ValueChangeEvent.fire(this, value);
        }
    }

    private void open() {
        list().addClass(resources.styles().openedList());
        everyLi().removeClass(resources.styles().hiddenLi(), resources.styles().dropDownArrow());
        $("li:first-child", list).addClass(resources.styles().dropDownArrow());

        opened = true;
    }

    private void clearBindings() {
        list().unbind(Event.ONBLUR);
        everyLi().unbind(Event.ONCLICK);
    }

    private GQuery list() {
        return $(list);
    }

    private GQuery everyLi() {
        return $("li", list);
    }
}
