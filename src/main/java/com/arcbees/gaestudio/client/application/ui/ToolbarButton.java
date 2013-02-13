package com.arcbees.gaestudio.client.application.ui;

import com.arcbees.gaestudio.client.resources.AppResources;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.assistedinject.Assisted;

import javax.inject.Inject;

public class ToolbarButton extends Composite {

    public interface Binder extends UiBinder<Widget, ToolbarButton> {
    }

    @UiField(provided = true)
    AppResources resources;
    @UiField
    HTMLPanel button;
    @UiField
    Image image;
    @UiField
    InlineLabel text;

    @Inject
    public ToolbarButton(final Binder binder, final AppResources resources, @Assisted final String text,
                         @Assisted final ImageResource imageResource, @Assisted final ToolbarButtonCallback callback) {
        this.resources = resources;

        initWidget(binder.createAndBindUi(this));

        this.text.setText(text);
        this.image.setResource(imageResource);

        button.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                callback.onClicked();
            }
        }, ClickEvent.getType());
    }

    public void setEnabled(Boolean enabled) {
        if (enabled) {
            button.removeStyleName(resources.styles().toolbarButtonDisabled());
        } else {
            button.addStyleName(resources.styles().toolbarButtonDisabled());
        }
    }

}
