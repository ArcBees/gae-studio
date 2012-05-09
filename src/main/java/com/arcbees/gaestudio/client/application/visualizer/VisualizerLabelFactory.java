/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.client.application.visualizer;

import com.arcbees.gaestudio.client.application.ui.LabelCallback;
import com.arcbees.gaestudio.client.application.visualizer.entitylist.EntityLabel;
import com.arcbees.gaestudio.client.application.visualizer.kind.KindLabel;
import com.arcbees.gaestudio.shared.dto.entity.Entity;

public interface VisualizerLabelFactory {

    KindLabel createKind(final String kind, final LabelCallback<String> callback);

    EntityLabel createEntity(final Entity entity, final LabelCallback<Entity> callback);

}
