package com.arcbees.gaestudio.client.application.visualizer.entitylist;

import com.arcbees.gaestudio.shared.dto.entity.EntityDTO;
import com.gwtplatform.mvp.client.UiHandlers;

public interface EntityListUiHandlers extends UiHandlers {

    void onEntityClicked(EntityDTO entityDTO);

    void refreshData();
}
