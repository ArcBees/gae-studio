/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.client.application.ui;

public abstract class SelectableLabelCallback<T> implements LabelCallback<T>{

    BaseLabel<T> selectedBaseLabel;

    @Override
    public void onClick(BaseLabel baseLabel, T id){
        if (selectedBaseLabel != null) {
            selectedBaseLabel.setSelected(false);
        }
        selectedBaseLabel = baseLabel;
        baseLabel.setSelected(true);
    }

}
