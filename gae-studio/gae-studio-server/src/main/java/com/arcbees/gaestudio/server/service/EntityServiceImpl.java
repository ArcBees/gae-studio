package com.arcbees.gaestudio.server.service;

import javax.inject.Inject;

import com.arcbees.gaestudio.server.util.AppEngineHelper;
import com.arcbees.gaestudio.server.util.DatastoreHelper;
import com.arcbees.gaestudio.shared.dto.entity.AppIdNamespaceDto;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.arcbees.gaestudio.shared.dto.entity.ParentKeyDto;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.common.base.Strings;

public class EntityServiceImpl implements EntityService {
    private final DatastoreHelper datastoreHelper;

    @Inject
    public EntityServiceImpl(DatastoreHelper datastoreHelper) {
        this.datastoreHelper = datastoreHelper;
    }

    @Override
    public Entity getEntity(Long entityId, String namespace, String appId, String kind, String parentId, String parentKind) {
        AppEngineHelper.disableApiHooks();

        Entity entity;
        ParentKeyDto parentKeyDto = null;

        if (!Strings.isNullOrEmpty(parentId) && !Strings.isNullOrEmpty(parentKind)) {
            parentKeyDto = new ParentKeyDto(parentKind, Long.valueOf(parentId));
        }

        KeyDto keyDto = new KeyDto(kind, entityId, parentKeyDto, new AppIdNamespaceDto(appId, namespace));

        try {
            entity = datastoreHelper.get(keyDto);
        } catch (EntityNotFoundException e) {
            return null;
        }

        return entity;
    }

    @Override
    public Entity updateEntity(Entity entity) {
        AppEngineHelper.disableApiHooks();

        entity.getKey();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(entity);

        return entity;
    }

    @Override
    public void deleteEntity(Key entityKey) {
        datastoreHelper.delete(entityKey, entityKey.getNamespace());
    }
}
