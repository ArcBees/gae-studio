/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.client.application.visualizer.entitylist;

import com.arcbees.gaestudio.client.Resources;
import com.arcbees.gaestudio.client.application.ui.BaseLabel;
import com.arcbees.gaestudio.client.application.ui.LabelCallback;
import com.arcbees.gaestudio.shared.dto.entity.EntityDTO;
import com.google.inject.assistedinject.Assisted;

import javax.inject.Inject;

public class EntityLabel extends BaseLabel {

    @Inject
    public EntityLabel(final Resources resources, @Assisted final EntityDTO entity,
                       @Assisted final LabelCallback callback) {
        super(resources, callback);
        updateContent(entity);
    }

    public void updateContent(EntityDTO entity) {
        String content = "[Id: " + entity.getKey().getId() + "] [" + entity
                .toString() + "]";
        setText(content);
    }

}
