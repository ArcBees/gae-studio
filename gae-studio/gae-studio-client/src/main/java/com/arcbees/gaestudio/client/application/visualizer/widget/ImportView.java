/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import com.arcbees.analytics.client.universalanalytics.UniversalAnalytics;
import com.arcbees.gaestudio.client.application.ui.AjaxLoader;
import com.arcbees.gaestudio.client.resources.AppResources;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewImpl;

import static com.arcbees.gaestudio.client.application.analytics.EventCategories.UI_ELEMENTS;
import static com.google.gwt.query.client.GQuery.$;

public class ImportView extends PopupViewImpl implements ImportPresenter.MyView {
    interface Binder extends UiBinder<Widget, ImportView> {
    }

    @UiField
    Button cancel;
    @UiField
    Button upload;
    @UiField
    SimplePanel uploadFormContainer;
    @UiField(provided = true)
    AjaxLoader ajaxLoader;
    @UiField
    AppResources res;

    private final UniversalAnalytics universalAnalytics;

    private UploadForm uploadForm;

    @Inject
    ImportView(Binder uiBinder,
               EventBus eventBus,
               AjaxLoader ajaxLoader,
               UniversalAnalytics universalAnalytics) {
        super(eventBus);

        this.ajaxLoader = ajaxLoader;
        this.universalAnalytics = universalAnalytics;

        initWidget(uiBinder.createAndBindUi(this));

        ajaxLoader.show();
    }

    @Override
    public void onFileChosen(String chosenFileName) {
        setUploadButtonEnabled(uploadForm.hasFileToUpload());
    }

    @Override
    public void prepareForNewUpload() {
        setUploadButtonEnabled(false);
    }

    @Override
    public void setUploadWidget(UploadForm uploadForm) {
        ajaxLoader.hide();

        this.uploadForm = uploadForm;
        uploadFormContainer.setWidget(uploadForm);
    }

    @UiHandler("upload")
    void onUploadClicked(ClickEvent event) {
        uploadForm.submit();
        ajaxLoader.show();

        universalAnalytics.sendEvent(UI_ELEMENTS, "click").eventLabel("Visualizer -> Import Popup -> Upload");
    }

    @UiHandler({"cancel", "close"})
    void onCancelClicked(ClickEvent event) {
        asPopupPanel().hide();

        universalAnalytics.sendEvent(UI_ELEMENTS, "click").eventLabel("Visualizer -> Import Popup -> Cancel or " +
                "Close");
    }

    private void setUploadButtonEnabled(boolean enabled) {
        $(upload).toggleClass(res.styles().gray(), !enabled);
        upload.setEnabled(enabled);
    }
}
