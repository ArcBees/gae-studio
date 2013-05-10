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
