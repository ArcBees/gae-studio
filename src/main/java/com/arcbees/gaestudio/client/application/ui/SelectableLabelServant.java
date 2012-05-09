/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.client.application.ui;

public class SelectableLabelServant {
    private BaseLabel selectedBaseLabel;

    public void select(BaseLabel baseLabel){
        if (selectedBaseLabel != null) {
            selectedBaseLabel.setSelected(false);
        }
        selectedBaseLabel = baseLabel;
        baseLabel.setSelected(true);
    }
}
