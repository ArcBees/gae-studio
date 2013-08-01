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
    public static DeleteEntitiesAction byKindAndNamespace(String kind, String namespace) {
        return new DeleteEntitiesAction(DeleteEntitiesType.KIND_NAMESPACE)
                .setKind(kind)
                .setNamespace(namespace);
    }

    public static DeleteEntitiesAction byKind(String kind) {
        return new DeleteEntitiesAction(DeleteEntitiesType.KIND)
                .setKind(kind);
    }

    public static DeleteEntitiesAction byNamespace(String namespace) {
        return new DeleteEntitiesAction(DeleteEntitiesType.NAMESPACE)
                .setNamespace(namespace);
    }

    public static DeleteEntitiesAction all() {
        return new DeleteEntitiesAction(DeleteEntitiesType.ALL);
    }

    private DeleteEntitiesType deleteEntitiesType;
    private String kind;
    private String namespace;

    protected DeleteEntitiesAction() {
        // Possibly for serialization.
    }

    private DeleteEntitiesAction(DeleteEntitiesType deleteEntitiesType) {
        this.deleteEntitiesType = deleteEntitiesType;
    }

    public DeleteEntitiesType getDeleteEntitiesType() {
        return deleteEntitiesType;
    }

    public String getKind() {
        return kind;
    }

    public String getNamespace() {
        return namespace;
    }

    private DeleteEntitiesAction setKind(String kind) {
        this.kind = kind;
        return this;
    }

    private DeleteEntitiesAction setNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }
}
