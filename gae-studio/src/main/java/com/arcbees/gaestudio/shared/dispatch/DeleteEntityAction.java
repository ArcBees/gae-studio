package com.arcbees.gaestudio.shared.dispatch;

import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.gwtplatform.dispatch.shared.Action;

public class DeleteEntityAction implements Action<DeleteEntityResult> {
    private EntityDto entityDTO;

    protected DeleteEntityAction() {
        // Possibly for serialization.
    }

    public DeleteEntityAction(EntityDto entityDTO) {
        this.entityDTO = entityDTO;
    }

    @Override
    public String getServiceName() {
        return Action.DEFAULT_SERVICE_NAME + "DeleteEntity";
    }

    @Override
    public boolean isSecured() {
        return false;
    }

    public EntityDto getEntityDTO() {
        return entityDTO;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DeleteEntityAction other = (DeleteEntityAction) obj;
        if (entityDTO == null) {
            if (other.entityDTO != null)
                return false;
        } else if (!entityDTO.equals(other.entityDTO))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 23;
        hashCode = (hashCode * 37) + (entityDTO == null ? 1 : entityDTO.hashCode());
        return hashCode;
    }

    @Override
    public String toString() {
        return "DeleteEntityAction[" + entityDTO + "]";
    }
}
