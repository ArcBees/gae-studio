package com.arcbees.gaestudio.shared.dispatch;

import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.gwtplatform.dispatch.shared.Result;

public class GetEntityDtoResult implements Result {
    private EntityDto entityDto;

    public GetEntityDtoResult() {
    }

    public EntityDto getEntityDto() {
        return entityDto;
    }

    public void setEntityDto(EntityDto entityDto) {
        this.entityDto = entityDto;
    }
}
