/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.profiler.ui;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.formatters.RecordFormatter;
import com.arcbees.gaestudio.client.resources.AppResources;
import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.arcbees.gaestudio.shared.dto.stacktrace.StackTraceElementDto;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiRenderer;

import static com.google.gwt.dom.client.Style.Display.BLOCK;
import static com.google.gwt.dom.client.Style.Display.NONE;
import static com.google.gwt.query.client.GQuery.$;

public class StatementCell extends AbstractCell<DbOperationRecordDto> {
    private RecordFormatter recordFormatter;

    public interface Renderer extends UiRenderer {
        void render(SafeHtmlBuilder safeHtmlBuilder, String formatted, String callLocation,
                    String statementDetailsClass, String imageClass);

        void onBrowserEvent(StatementCell cell, NativeEvent event, Element element, DbOperationRecordDto dto);

        DivElement getDetails(Element parent);

        SpanElement getImgContainer(Element parent);
    }

    private final Renderer renderer;
    private final AppResources appResources;

    @Inject
    StatementCell(RecordFormatter recordFormatter,
                  Renderer renderer,
                  AppResources appResources) {
        super(BrowserEvents.CLICK, BrowserEvents.MOUSEOVER, BrowserEvents.MOUSEOUT);

        this.recordFormatter = recordFormatter;
        this.renderer = renderer;
        this.appResources = appResources;
    }

    @Override
    public void onBrowserEvent(Context context, Element parent, DbOperationRecordDto value, NativeEvent event,
                               ValueUpdater<DbOperationRecordDto> valueUpdater) {
        renderer.onBrowserEvent(this, event, parent, value);
    }

    @Override
    public void render(Context context, DbOperationRecordDto record, SafeHtmlBuilder safeHtmlBuilder) {
        String formatted = recordFormatter.formatRecord(record);
        String callLocation = tempFormatCaller(record.getCallerStackTraceElement());

        renderer.render(safeHtmlBuilder, formatted, callLocation, appResources.styles().statementDetails(),
                appResources.styles().statementImage());
    }

    @UiHandler({"imgContainer"})
    void onRemoveImgContainerClicked(ClickEvent event, Element parent, DbOperationRecordDto value) {
        DivElement details = renderer.getDetails(parent);
        toggleDetails(details);

        SpanElement imgContainer = getIcon(parent);
        toggleIconState(imgContainer);
    }

    @UiHandler({"imgContainer"})
    void onRemoveImgContainerMouseOver(MouseOverEvent event, Element parent, DbOperationRecordDto value) {
        SpanElement imgContainer = getIcon(parent);
        imgContainer.addClassName(appResources.styles().statementImageRl());
    }

    @UiHandler({"imgContainer"})
    void onRemoveImgContainerMouseOut(MouseOutEvent event, Element parent, DbOperationRecordDto value) {
        SpanElement imgContainer = getIcon(parent);
        imgContainer.removeClassName(appResources.styles().statementImageRl());
    }

    private SpanElement getIcon(Element parent) {
        return renderer.getImgContainer(parent);
    }

    private void toggleIconState(SpanElement imgContainer) {
        AppResources.Styles styles = appResources.styles();

        if($(imgContainer).hasClass(styles.statementImageDn())) {
            imgContainer.removeClassName(styles.statementImageDn());
        } else {
            imgContainer.addClassName(styles.statementImageDn());
        }
    }

    private void toggleDetails(DivElement details) {
        Style style = details.getStyle();
        String display = style.getDisplay();

        if(display.isEmpty()) {
            display = NONE.getCssName();
        }

        if (display.equals(NONE.getCssName())) {
            style.setDisplay(BLOCK);
        } else {
            style.setDisplay(NONE);
        }
    }

    private String tempFormatCaller(StackTraceElementDto caller) {
        StringBuilder builder = new StringBuilder();

        builder.append("Class:");
        builder.append(caller.getClassName());
        builder.append(" Method:");
        builder.append(caller.getMethodName());
        builder.append(" Filename:");
        builder.append(caller.getFileName());
        builder.append(" Line#:");
        builder.append(caller.getLineNumber());

        return builder.toString();
    }
}
