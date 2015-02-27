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

package com.arcbees.gaestudio.client.application.visualizer.widget;

import com.arcbees.analytics.shared.Analytics;
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
    @UiField
    AppResources res;

    private final Analytics analytics;

    private UploadForm uploadForm;

    @Inject
    ImportView(
            Binder uiBinder,
            EventBus eventBus,
            Analytics analytics) {
        super(eventBus);

        this.analytics = analytics;

        initWidget(uiBinder.createAndBindUi(this));
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
        this.uploadForm = uploadForm;
        uploadFormContainer.setWidget(uploadForm);
    }

    @UiHandler("upload")
    void onUploadClicked(ClickEvent event) {
        uploadForm.submit();

        analytics.sendEvent(UI_ELEMENTS, "click").eventLabel("Visualizer -> Import Popup -> Upload");
    }

    @UiHandler({"cancel", "close"})
    void onCancelClicked(ClickEvent event) {
        asPopupPanel().hide();

        analytics.sendEvent(UI_ELEMENTS, "click")
                .eventLabel("Visualizer -> Import Popup -> Cancel or Close");
    }

    private void setUploadButtonEnabled(boolean enabled) {
        $(upload).toggleClass(res.styles().gray(), !enabled);
        upload.setEnabled(enabled);
    }
}
