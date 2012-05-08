package com.arcbees.gaestudio.client.application.visualizer;

import com.arcbees.gaestudio.shared.dto.entity.Key;
import com.gwtplatform.mvp.client.UiHandlers;

public interface EntityListUiHandlers extends UiHandlers {
    
    void onEntityClicked(Key entityKey, String entityData);
    
}
