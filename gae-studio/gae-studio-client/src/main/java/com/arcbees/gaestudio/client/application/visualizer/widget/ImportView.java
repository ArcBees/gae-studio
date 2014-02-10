/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget;

import com.arcbees.gaestudio.client.application.ui.AjaxLoader;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.gwtplatform.mvp.client.PopupViewWithUiHandlers;

public class ImportView extends PopupViewWithUiHandlers<ImportUiHandlers> implements ImportPresenter.MyView {
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

    private UploadForm uploadForm;

    @Inject
    ImportView(Binder uiBinder,
               EventBus eventBus,
               AjaxLoader ajaxLoader) {
        super(eventBus);

        this.ajaxLoader = ajaxLoader;

        initWidget(uiBinder.createAndBindUi(this));

        ajaxLoader.show();
    }

    @Override
    public void onFileChosen(String chosenFileName) {
        upload.setEnabled(uploadForm.hasFileToUpload());
    }

    @Override
    public void prepareForNewUpload() {
        upload.setEnabled(false);
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
    }

    @UiHandler("cancel")
    void onCancelClicked(ClickEvent event) {
        asPopupPanel().hide();
    }
}
