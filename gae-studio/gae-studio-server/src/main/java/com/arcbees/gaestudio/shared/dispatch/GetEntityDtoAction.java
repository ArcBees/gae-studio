/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

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