package com.arcbees.gaestudio.client.gin;

import com.arcbees.gaestudio.client.Resources;
import com.arcbees.gaestudio.client.application.widget.message.ui.MessageResources;
import com.google.inject.Inject;

public class ResourceLoader {
    @Inject
    public ResourceLoader(Resources resources, MessageResources messageResources) {
        resources.styles().ensureInjected();
        resources.sprites().ensureInjected();
        messageResources.styles().ensureInjected();
    }
}
