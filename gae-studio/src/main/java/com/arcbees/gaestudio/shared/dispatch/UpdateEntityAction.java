package com.arcbees.gaestudio.shared.dispatch;

import com.arcbees.gaestudio.shared.dispatch.util.GaeStudioActionImpl;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;

public class UpdateEntityAction extends GaeStudioActionImpl<UpdateEntityResult> {
    private EntityDto entityDTO;

    protected UpdateEntityAction() {
        // Possibly for serialization.
    }

    public UpdateEntityAction(EntityDto entityDTO) {
        this.entityDTO = entityDTO;
    }

    public EntityDto getEntityDTO() {
        return entityDTO;
    }
}
