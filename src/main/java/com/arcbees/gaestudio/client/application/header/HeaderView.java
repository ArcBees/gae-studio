package com.arcbees.gaestudio.client.application.header;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.client.Resources;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class HeaderView extends ViewWithUiHandlers<HeaderUiHandlers> implements HeaderPresenter.MyView {

    public interface Binder extends UiBinder<Widget, HeaderView> {
    }

    @UiField(provided = true)
    Resources resources;

    @UiField
    Button stopRecording;

    @UiField
    Button startRecording;

    @Inject
    public HeaderView(final Binder uiBinder, final Resources resources, final UiHandlersStrategy<HeaderUiHandlers>
            uiHandlersStrategy) {
        super(uiHandlersStrategy);

        this.resources = resources;
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiHandler("startRecording")
    void onStartRecordingClicked(ClickEvent event) {
        setRecordingState(true);
        getUiHandlers().onToggleRecording(true);
    }

    @UiHandler("stopRecording")
    void onStopRecordingClicked(ClickEvent event) {
        setRecordingState(false);
        getUiHandlers().onToggleRecording(false);
    }

    @Override
    public void setRecordingState(boolean start) {
        startRecording.setVisible(!start);
        stopRecording.setVisible(start);
    }

}
