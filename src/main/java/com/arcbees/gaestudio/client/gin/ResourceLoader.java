package com.arcbees.gaestudio.client.gin;

import com.arcbees.gaestudio.client.AppResources;
import com.arcbees.gaestudio.client.application.widget.message.ui.MessageResources;
import com.google.inject.Inject;

public class ResourceLoader {
    @Inject
    public ResourceLoader(AppResources resources, MessageResources messageResources) {
        resources.styles().ensureInjected();
        resources.sprites().ensureInjected();
        messageResources.styles().ensureInjected();
    }
}
