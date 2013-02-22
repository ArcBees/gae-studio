package com.arcbees.gaestudio.client.application.widget.message.ui;

import com.arcbees.gaestudio.client.application.widget.message.Message;
import com.arcbees.gaestudio.client.application.widget.message.MessageCloseDelay;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.query.client.Function;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineHTML;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;

import javax.inject.Inject;

import static com.google.gwt.query.client.GQuery.$;

public class MessageWidget extends Composite {
    public interface Binder extends UiBinder<Widget, MessageWidget> {
    }

    @UiField
    HTMLPanel messageBox;
    @UiField
    InlineHTML close;
    @UiField(provided = true)
    MessageResources messageResources;
    @UiField
    InlineLabel messageLabel;

    private final Message message;

    private Timer closeTimer = new Timer() {
        @Override
        public void run() {
            close();
        }
    };

    @Inject
    public MessageWidget(final Binder binder, final MessageResources messageResources,
                         @Assisted final Message message) {
        this.messageResources = messageResources;
        this.message = message;

        initWidget(binder.createAndBindUi(this));
        initContent();
        initTimeout();
    }

    @Override
    protected void onLoad() {
        $(messageBox).fadeIn();
    }

    @UiHandler("close")
    void onCloseAnchorClicked(ClickEvent event) {
        close();
    }

    private void close() {
        $(messageBox).fadeOut(new Function() {
            @Override
            public void f() {
                MessageWidget.this.removeFromParent();
            }
        });
    }

    private void initContent() {
        messageLabel.setText(message.getMessage());

        switch (message.getStyle()) {
            case SUCCESS:
                messageBox.addStyleName(messageResources.styles().success());
                break;
            case ERROR:
                messageBox.addStyleName(messageResources.styles().error());
                break;
        }
    }

    private void initTimeout() {
        if (!message.getCloseDelay().equals(MessageCloseDelay.NEVER)) {
            closeTimer.schedule(message.getCloseDelay().getDelay());
        }
    }
}
