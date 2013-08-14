package com.arcbees.gaestudio.server.rest;

import javax.inject.Inject;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.arcbees.gaestudio.server.dto.mapper.EntityMapper;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.shared.dto.entity.KeyDto;
import com.arcbees.gaestudio.testutil.GaeTestBase;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import static org.junit.Assert.assertEquals;

@RunWith(JukitoRunner.class)
public class EntityResourceTest extends GaeTestBase {
    public static class Module extends JukitoModule {
        @Override
        protected void configureTest() {
            install(new FactoryModuleBuilder().build(SubresourceFactory.class));
        }
    }
    private static final String KIND_NAME = "FakeEntity";
    private static final String PROPERTY_NAME = "property-name";
    private static final String A_NAME = "a-name";
    private static final String ANOTHER_NAME = "another-name";

    @Inject
    EntitiesResource entitiesResource;

    @Test
    public void entityStored_getEntity_shouldReturnSameEntity() throws EntityNotFoundException {
        //given
        Entity sentEntity = createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        Long entityId = sentEntity.getKey().getId();

        //when
        Entity savedEntity = getEntityFromEntityResource(entityId);

        //then
        assertEquals(sentEntity, savedEntity);
    }

    @Test
    public void entityStored_updateEntity_shouldUpdateEntity() throws EntityNotFoundException {
        //given
        Entity sentEntity = createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        Long entityId = sentEntity.getKey().getId();

        //when
        sentEntity.setProperty(PROPERTY_NAME, ANOTHER_NAME);
        EntityDto entityDto = EntityMapper.mapEntityToDto(sentEntity);

        entitiesResource.getEntityResource(entityId).updateEntity(entityDto);

        //then
        Entity savedEntity = getEntityFromEntityResource(entityId);

        assertEquals(ANOTHER_NAME, savedEntity.getProperty(PROPERTY_NAME));
    }

    @Test(expected=EntityNotFoundException.class)
    public void entityStored_deleteEntity_shouldDeleteEntity() throws EntityNotFoundException {
        //given
        Entity sentEntity = createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        Key entityKey = sentEntity.getKey();
        Long entityId = entityKey.getId();

        //when
        KeyDto entityKeyDto = EntityMapper.mapKeyToKeyDto(entityKey);
        entitiesResource.getEntityResource(entityId).deleteEntity(entityKeyDto);

        //then
        getEntityFromEntityResource(entityId);
    }

    private Entity getEntityFromEntityResource(Long id) throws EntityNotFoundException {
        EntityResource entityResource = entitiesResource.getEntityResource(id);

        EntityDto entityDto = entityResource.getEntity(null, null, KIND_NAME, null, null);

        return EntityMapper.mapDtoToEntity(entityDto);
    }
}
