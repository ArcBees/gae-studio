/**
 * Copyright 2015 ArcBees Inc.
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

package com.arcbees.gaestudio.client.application.profiler.ui;

import javax.inject.Inject;

import com.arcbees.gaestudio.client.formatters.RecordFormatter;
import com.arcbees.gaestudio.client.resources.AppMessages;
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
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiRenderer;
import com.google.gwt.user.client.ui.Widget;

import static com.google.gwt.dom.client.Style.Display.BLOCK;
import static com.google.gwt.dom.client.Style.Display.NONE;
import static com.google.gwt.query.client.GQuery.$;

public class StatementCell extends AbstractCell<DbOperationRecordDto> {
    public interface Renderer extends UiRenderer {
        void render(SafeHtmlBuilder safeHtmlBuilder, String formatted, String callLocation,
                String statementDetailsClass, String imageClass);

        void onBrowserEvent(StatementCell cell, NativeEvent event, Element element, DbOperationRecordDto dto);

        DivElement getDetails(Element parent);

        SpanElement getImgContainer(Element parent);
    }

    interface Binder extends UiBinder<Widget, StatementCell> {
    }

    @UiField
    SpanElement imgContainer;

    private final Renderer renderer;
    private final AppResources appResources;
    private final AppMessages appMessages;

    private RecordFormatter recordFormatter;

    @Inject
    StatementCell(
            RecordFormatter recordFormatter,
            Renderer renderer,
            AppResources appResources,
            AppMessages appMessages) {
        super(BrowserEvents.CLICK, BrowserEvents.MOUSEOVER, BrowserEvents.MOUSEOUT);

        this.recordFormatter = recordFormatter;
        this.renderer = renderer;
        this.appResources = appResources;
        this.appMessages = appMessages;
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

    @UiHandler("imgContainer")
    void onRemoveImgContainerClicked(ClickEvent event, Element parent) {
        DivElement details = renderer.getDetails(parent);
        toggleDetails(details);

        SpanElement imgContainer = getIcon(parent);
        toggleIconState(imgContainer);
    }

    @UiHandler("imgContainer")
    void onRemoveImgContainerMouseOver(MouseOverEvent event, Element parent) {
        SpanElement imgContainer = getIcon(parent);
        imgContainer.addClassName(appResources.styles().statementImageRl());
    }

    @UiHandler("imgContainer")
    void onRemoveImgContainerMouseOut(MouseOutEvent event, Element parent) {
        SpanElement imgContainer = getIcon(parent);
        imgContainer.removeClassName(appResources.styles().statementImageRl());
    }

    private SpanElement getIcon(Element parent) {
        return renderer.getImgContainer(parent);
    }

    private void toggleIconState(SpanElement imgContainer) {
        AppResources.Styles styles = appResources.styles();

        $(imgContainer).toggleClass(styles.statementImageDn());
    }

    private void toggleDetails(DivElement details) {
        Style style = details.getStyle();
        String display = style.getDisplay();

        if (display.isEmpty()) {
            display = NONE.getCssName();
        }

        if (display.equals(NONE.getCssName())) {
            style.setDisplay(BLOCK);
        } else {
            style.setDisplay(NONE);
        }
    }

    private String tempFormatCaller(StackTraceElementDto caller) {
        return appMessages.callLocationDetails(caller.getClassName(), caller.getMethodName(),
                caller.getFileName(), String.valueOf(caller.getLineNumber()));
    }
}
