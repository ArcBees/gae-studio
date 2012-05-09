/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.client.application.visualizer.entitylist;

import com.arcbees.gaestudio.client.Resources;
import com.arcbees.gaestudio.client.application.ui.BaseLabel;
import com.arcbees.gaestudio.client.application.ui.LabelCallback;
import com.arcbees.gaestudio.shared.dto.entity.Entity;
import com.google.inject.assistedinject.Assisted;

import javax.inject.Inject;

public class EntityLabel extends BaseLabel<Entity> {

    @Inject
    public EntityLabel(final Resources resources, @Assisted final Entity entity,
                       @Assisted final LabelCallback<Entity> callback) {
        super(resources, entity, callback);
        updateContent(entity);
    }

    public void updateContent(Entity entity) {
        String content = "[Id: " + entity.getKey().getId() + "] [" + entity
                .toString() + "]";
        setText(content);
    }

}
