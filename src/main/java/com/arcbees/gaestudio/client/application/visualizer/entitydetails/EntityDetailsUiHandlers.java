package com.arcbees.gaestudio.client.application.visualizer.entitydetails;

import com.arcbees.gaestudio.shared.dto.entity.EntityDTO;
import com.gwtplatform.mvp.client.UiHandlers;

public interface EntityDetailsUiHandlers extends UiHandlers {

    void editEntity(EntityDTO entityDTO);

}
