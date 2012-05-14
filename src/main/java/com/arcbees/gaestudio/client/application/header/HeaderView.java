package com.arcbees.gaestudio.client.application.header;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.client.Resources;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class HeaderView extends ViewWithUiHandlers<HeaderUiHandlers> implements HeaderPresenter.MyView {

    public interface Binder extends UiBinder<Widget, HeaderView> {
    }

    @UiField(provided = true)
    Resources resources;

    @UiField
    Button recording;

    private Boolean isRecording = false;

    @Inject
    public HeaderView(final Binder uiBinder, final Resources resources, final UiHandlersStrategy<HeaderUiHandlers>
            uiHandlersStrategy) {
        super(uiHandlersStrategy);

        this.resources = resources;
        initWidget(uiBinder.createAndBindUi(this));
        setRecordingState();

        Window.addCloseHandler(new CloseHandler<Window>() {
            @Override
            public void onClose(CloseEvent<Window> windowCloseEvent) {
                getUiHandlers().onToggleRecording(false);
            }
        });
    }

    @UiHandler("recording")
    void onRecordingClicked(ClickEvent event) {
        isRecording = !isRecording;
        getUiHandlers().onToggleRecording(isRecording);
        setRecordingState();
    }

    @Override
    public void setPending(Boolean isPending) {
        recording.setEnabled(!isPending);
    }

    private void setRecordingState() {
        String recordingText = (isRecording) ? "Stop recording" : "Start recording";
        recording.setText(recordingText);
    }

}
