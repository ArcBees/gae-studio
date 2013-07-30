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

public class DeleteEntitiesAction extends GaeStudioActionImpl<DeleteEntitiesResult> {
    public static DeleteEntitiesAction create(DeleteEntitiesType deleteType, String deleteTypeValue) {
        switch (deleteType) {
            case KIND:
                return byKind(deleteTypeValue);
            case NAMESPACE:
                return byNamespace(deleteTypeValue);
            default:
                return all();
        }
    }

    public static DeleteEntitiesAction byKind(String kind) {
        return new DeleteEntitiesAction(DeleteEntitiesType.KIND, kind);
    }

    public static DeleteEntitiesAction byNamespace(String namespace) {
        return new DeleteEntitiesAction(DeleteEntitiesType.NAMESPACE, namespace);
    }

    public static DeleteEntitiesAction all() {
        return new DeleteEntitiesAction(DeleteEntitiesType.ALL, "");
    }

    private DeleteEntitiesType deleteEntitiesType;

    private String value;

    protected DeleteEntitiesAction() {
        // Possibly for serialization.
    }

    private DeleteEntitiesAction(DeleteEntitiesType deleteEntitiesType) {
        this(deleteEntitiesType, null);
    }

    private DeleteEntitiesAction(DeleteEntitiesType deleteEntitiesType,
                                 String value) {
        this.deleteEntitiesType = deleteEntitiesType;
        this.value = value;
    }

    public DeleteEntitiesType getDeleteEntitiesType() {
        return deleteEntitiesType;
    }

    public String getValue() {
        return value;
    }
}
