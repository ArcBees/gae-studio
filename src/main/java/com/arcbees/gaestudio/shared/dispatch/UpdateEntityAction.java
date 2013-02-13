package com.arcbees.gaestudio.shared.dispatch;

import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.gwtplatform.dispatch.shared.Action;

public class UpdateEntityAction implements Action<UpdateEntityResult> {
    private EntityDto entityDTO;

    protected UpdateEntityAction() {
        // Possibly for serialization.
    }

    public UpdateEntityAction(EntityDto entityDTO) {
        this.entityDTO = entityDTO;
    }

    @Override
    public String getServiceName() {
        return Action.DEFAULT_SERVICE_NAME + "UpdateEntity";
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
        UpdateEntityAction other = (UpdateEntityAction) obj;
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
        return "UpdateEntityAction[" + entityDTO + "]";
    }
}
