package com.arcbees.gaestudio.client.application.profiler.widget;

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

public class ProfilerToolbarView extends ViewWithUiHandlers<ProfilerToolbarUiHandlers> implements
        ProfilerToolbarPresenter.MyView {

    public interface Binder extends UiBinder<Widget, ProfilerToolbarView> {
    }

    @UiField(provided = true)
    Resources resources;
    @UiField
    Button record;
    @UiField
    Button stop;
    @UiField
    Button clear;

    private Boolean isRecording = false;

    @Inject
    public ProfilerToolbarView(final Binder uiBinder, final Resources resources,
                               final UiHandlersStrategy<ProfilerToolbarUiHandlers> uiHandlersStrategy) {
        super(uiHandlersStrategy);

        this.resources = resources;
        initWidget(uiBinder.createAndBindUi(this));
        setRecordingState(false);

        Window.addCloseHandler(new CloseHandler<Window>() {
            @Override
            public void onClose(CloseEvent<Window> windowCloseEvent) {
                if (isRecording) {
                    getUiHandlers().onToggleRecording(false);
                }
            }
        });
    }

    @Override
    public void setRecordingState(Boolean isRecording) {
        this.isRecording = isRecording;
        record.setEnabled(!isRecording);
        stop.setEnabled(isRecording);
        clear.setEnabled(!isRecording);
    }

    @UiHandler("record")
    void onRecordClicked(ClickEvent event) {
        isRecording = true;
        getUiHandlers().onToggleRecording(true);
    }

    @UiHandler("stop")
    void onStopClicked(ClickEvent event) {
        isRecording = false;
        getUiHandlers().onToggleRecording(false);
    }

    @UiHandler("clear")
    void onClearClicked(ClickEvent event){
        getUiHandlers().clearOperationRecords();
    }

}
