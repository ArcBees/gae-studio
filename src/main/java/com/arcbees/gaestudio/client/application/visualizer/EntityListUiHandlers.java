package com.arcbees.gaestudio.client.application.visualizer;

import com.arcbees.gaestudio.shared.dto.entity.KeyDTO;
import com.gwtplatform.mvp.client.UiHandlers;

public interface EntityListUiHandlers extends UiHandlers {
    
    void onEntityClicked(KeyDTO entityKey, String entityData);
    
}
