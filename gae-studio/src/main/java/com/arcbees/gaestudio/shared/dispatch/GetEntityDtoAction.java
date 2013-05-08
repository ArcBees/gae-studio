package com.arcbees.gaestudio.shared.dispatch;

import com.arcbees.gaestudio.shared.dispatch.util.GaeStudioActionImpl;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;

public class GetEntityDtoAction extends GaeStudioActionImpl<GetEntityDtoResult> {
    private KeyDto keyDto;

    public GetEntityDtoAction() {
    }

    public KeyDto getKeyDto() {
        return keyDto;
    }

    public void setKeyDto(KeyDto keyDto) {
        this.keyDto = keyDto;
    }
}
