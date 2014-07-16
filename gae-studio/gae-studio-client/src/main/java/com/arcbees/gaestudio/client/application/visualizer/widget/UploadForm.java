/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import javax.inject.Inject;

import com.arcbees.analytics.client.universalanalytics.UniversalAnalytics;
import com.arcbees.gaestudio.client.resources.AppResources;
import com.arcbees.gaestudio.shared.channel.Constants;
import com.arcbees.gaestudio.shared.config.AppConfig;
import com.google.common.base.Strings;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.BrowserEvents;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.query.client.Function;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;

import static com.arcbees.gaestudio.client.application.analytics.EventCategories.UI_ELEMENTS;
import static com.google.gwt.query.client.GQuery.$;

public class UploadForm implements IsWidget, FormPanel.SubmitCompleteHandler {
    public interface Handler {
        void onFileChosen(String chosenFileName);

        void onUploadFailure(String errorMessage);

        void onUploadSuccess();

        void onSubmit();
    }

    private static class UploadResponse extends JavaScriptObject {
        protected UploadResponse() {
        }

        public final native boolean isSuccess() /*-{
            return this.success;
        }-*/;

        public final native String getMessage() /*-{
            return this.message;
        }-*/;
    }

    private static final String FILE_UPLOAD = "fileUpload";

    private final FileUpload fileUpload = new FileUpload();
    private final FormPanel formPanel;
    private final Handler handler;
    private final Label selectedFile;
    private final AppResources resources;
    private final UniversalAnalytics universalAnalytics;
    private final AppConfig appConfig;

    @Inject
    UploadForm(AppResources resources,
               UniversalAnalytics universalAnalytics,
               AppConfig appConfig,
               @Assisted String uploadUrl,
               @Assisted Handler handler) {
        this.handler = handler;
        this.resources = resources;
        this.universalAnalytics = universalAnalytics;
        this.appConfig = appConfig;

        formPanel = new FormPanel();
        selectedFile = new Label("...");
        selectedFile.setStyleName(resources.styles().uploadLabel());

        fileUpload.setName(FILE_UPLOAD);
        fileUpload.setVisible(false);

        formPanel.add(hideFileUploadWithButton("Choose File"));
        formPanel.setAction(uploadUrl);
        formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
        formPanel.setMethod(FormPanel.METHOD_POST);
        formPanel.addSubmitCompleteHandler(this);
    }

    public boolean hasFileToUpload() {
        return !Strings.isNullOrEmpty(fileUpload.getFilename());
    }

    @Override
    public Widget asWidget() {
        return formPanel;
    }

    @Override
    public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
        handleSubmitComplete(event.getResults());
        formPanel.reset();
    }

    public void submit() {
        if (hasFileToUpload()) {
            formPanel.submit();
            handler.onSubmit();
        }
    }

    private void handleSubmitComplete(String result) {
        UploadResponse uploadResponse = getJsonObjectFromHtml(result);

        if (uploadResponse.isSuccess()) {
            handler.onUploadSuccess();
        } else {
            handler.onUploadFailure(uploadResponse.getMessage());
        }
    }

    private Panel hideFileUploadWithButton(String buttonText) {
        FlowPanel flowPanel = new FlowPanel();
        flowPanel.add(fileUpload);

        Button button = new Button(buttonText, new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                registerFileChangedHandler();
                $(fileUpload).click();
                universalAnalytics.sendEvent(UI_ELEMENTS, "click")
                        .eventLabel("Visualizer -> Upload Form -> Choose File");
            }
        });
        button.setStyleName(resources.styles().button());
        button.addStyleName(resources.styles().chooseFileButton());
        flowPanel.add(selectedFile);
        flowPanel.add(button);
        Hidden hidden = new Hidden(Constants.CLIENT_ID, appConfig.getClientId());
        flowPanel.add(hidden);

        return flowPanel;
    }

    private void registerFileChangedHandler() {
        $(fileUpload.getElement()).unbind(BrowserEvents.CHANGE);
        $(fileUpload.getElement()).change(new Function() {
            @Override
            public void f() {
                String path = fileUpload.getFilename();
                String fileName = extractFileNameFromPath(path);
                selectedFile.setText(fileName);
                handler.onFileChosen(fileName);
            }
        });
    }

    private String extractFileNameFromPath(String path) {
        Integer lastIndex = path.replace("\\", "/").lastIndexOf("/");

        return path.substring(lastIndex + 1);
    }

    private UploadResponse getJsonObjectFromHtml(String result) {
        HTML htmlParser = new HTML(result);
        String json = htmlParser.getText().trim();

        JSONValue jsonValue = JSONParser.parseStrict(json);
        JSONObject object = jsonValue.isObject();

        return object.getJavaScriptObject().cast();
    }
}
