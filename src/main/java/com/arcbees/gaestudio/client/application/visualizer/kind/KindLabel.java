/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.client.application.visualizer.kind;

import com.arcbees.gaestudio.client.Resources;
import com.arcbees.gaestudio.client.application.ui.BaseLabel;
import com.arcbees.gaestudio.client.application.ui.LabelCallback;
import com.google.inject.assistedinject.Assisted;

import javax.inject.Inject;

public class KindLabel extends BaseLabel {

    @Inject
    public KindLabel(final Resources resources, @Assisted final String kind,
                     @Assisted final LabelCallback callback) {
        super(resources, callback);
        updateContent(kind);
    }

    public void updateContent(String kind) {
        setText(kind);
    }

}
