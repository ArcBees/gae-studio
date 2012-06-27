package com.arcbees.gaestudio.client.application.profiler.widget;

import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.arcbees.gaestudio.client.Resources;
import com.arcbees.gaestudio.client.application.ui.ToolbarButton;
import com.arcbees.gaestudio.client.application.ui.ToolbarButtonCallback;
import com.arcbees.gaestudio.client.application.ui.UiFactory;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class ProfilerToolbarView extends ViewWithUiHandlers<ProfilerToolbarUiHandlers> implements
        ProfilerToolbarPresenter.MyView {

    public interface Binder extends UiBinder<Widget, ProfilerToolbarView> {
    }

    @UiField(provided = true)
    Resources resources;
    @UiField
    HTMLPanel buttons;

    private final UiFactory uiFactory;
    private final ToolbarButton record;
    private final ToolbarButton stop;
    private final ToolbarButton clear;
    private Boolean isRecording = false;

    @Inject
    public ProfilerToolbarView(final Binder uiBinder, final Resources resources, final UiFactory uiFactory,
                               final UiHandlersStrategy<ProfilerToolbarUiHandlers> uiHandlersStrategy) {
        super(uiHandlersStrategy);

        this.resources = resources;
        this.uiFactory = uiFactory;
        initWidget(uiBinder.createAndBindUi(this));

        stopRecordindWhenWindowIsClosed();

        record = createRecordButton();
        stop = createStopButton();
        clear = createClearButton();

        buttons.add(record);
        buttons.add(stop);
        buttons.add(clear);

        setRecordingState(false);
    }

    private void stopRecordindWhenWindowIsClosed() {
        Window.addCloseHandler(new CloseHandler<Window>() {
            @Override
            public void onClose(CloseEvent<Window> windowCloseEvent) {
                if (isRecording) {
                    getUiHandlers().onToggleRecording(false);
                }
            }
        });
    }

    private ToolbarButton createRecordButton() {
        return uiFactory.createToolbarButton("Record", resources.record(), new ToolbarButtonCallback() {
            @Override
            public void onClicked() {
                isRecording = true;
                getUiHandlers().onToggleRecording(true);
            }
        });
    }

    private ToolbarButton createStopButton() {
        return uiFactory.createToolbarButton("Stop", resources.stop(), new ToolbarButtonCallback() {
            @Override
            public void onClicked() {
                isRecording = false;
                getUiHandlers().onToggleRecording(false);
            }
        });
    }

    private ToolbarButton createClearButton() {
        return uiFactory.createToolbarButton("Clear", resources.delete(), new ToolbarButtonCallback() {
            @Override
            public void onClicked() {
                getUiHandlers().clearOperationRecords();
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
}
