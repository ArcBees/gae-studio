/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.client.application.visualizer;

import com.arcbees.gaestudio.client.application.ui.LabelCallback;
import com.arcbees.gaestudio.client.application.visualizer.entitylist.EntityLabel;
import com.arcbees.gaestudio.client.application.visualizer.kind.KindLabel;
import com.arcbees.gaestudio.shared.dto.entity.EntityDTO;

public interface VisualizerLabelFactory {

    KindLabel createKind(final String kind, final LabelCallback callback);

    EntityLabel createEntity(final EntityDTO entity, final LabelCallback callback);

}
