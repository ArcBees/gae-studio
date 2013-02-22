package com.arcbees.gaestudio.shared.dispatch;

import com.arcbees.gaestudio.shared.dispatch.util.GaeStudioActionImpl;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;

public class DeleteEntityAction extends GaeStudioActionImpl<DeleteEntityResult> {
    private EntityDto entityDTO;

    protected DeleteEntityAction() {
        // Possibly for serialization.
    }

    public DeleteEntityAction(EntityDto entityDTO) {
        this.entityDTO = entityDTO;
    }

    public EntityDto getEntityDTO() {
        return entityDTO;
    }
}
