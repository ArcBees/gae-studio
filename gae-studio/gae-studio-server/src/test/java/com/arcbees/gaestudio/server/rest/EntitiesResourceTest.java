package com.arcbees.gaestudio.server.rest;

import java.util.List;

import javax.inject.Inject;

import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.arcbees.gaestudio.server.dto.mapper.EntityMapper;
import com.arcbees.gaestudio.shared.DeleteEntities;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.testutil.GaeTestBase;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(JukitoRunner.class)
public class EntitiesResourceTest extends GaeTestBase {
    private static final String KIND_NAME = "FakeEntity";
    private static final String GAE_KIND_NAME = "__FakeEntity";
    private static final String PROPERTY_NAME = "property-name";
    private static final String A_NAME = "a-name";
    private static final String ANOTHER_NAME = "another-name";

    @Inject
    EntitiesResource entitiesResource;

    @Test
    public void entityStored_createEmptyEntity_shouldReturnEmptyEntity() throws EntityNotFoundException {
        //given
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);

        //when
        EntityDto entityDto = entitiesResource.createEmptyEntity(KIND_NAME);

        //then
        Entity entity = EntityMapper.mapDtoToEntity(entityDto);

        assertNotNull(entity);
        assertEquals(KIND_NAME, entity.getKind());
        assertEquals("", entity.getProperty(PROPERTY_NAME));
    }

    @Test
    public void twoEntitiesStored_getCount_shouldReturnTwoEntities() {
        //given
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, ANOTHER_NAME);

        //when
        Integer entityCount = entitiesResource.getCount(KIND_NAME);

        //then
        assertEquals(2l, (long) entityCount);
    }

    @Test
    public void twoEntitiesStored_deleteEntitiesByKind_shouldHaveNoMoreEntities() {
        //given
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, ANOTHER_NAME);

        //when
        entitiesResource.deleteEntities(KIND_NAME, null, DeleteEntities.KIND);

        //then
        assertEquals(0l, (long) entitiesResource.getCount(KIND_NAME));
    }

    @Test
    public void twoEntitiesStored_deleteEntitiesByNamespace_shouldHaveNoMoreEntities() {
        //given
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        createEntityInDatastore(GAE_KIND_NAME, PROPERTY_NAME, ANOTHER_NAME);

        //when
        entitiesResource.deleteEntities(null, "", DeleteEntities.NAMESPACE);

        //then
        assertEquals(1l, (long) entitiesResource.getCount(GAE_KIND_NAME));
    }

    @Test
    public void twoEntitiesStored_getEntities_shouldReturnTwoEntities() {
        //given
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, A_NAME);
        createEntityInDatastore(KIND_NAME, PROPERTY_NAME, ANOTHER_NAME);

        //when
        List<EntityDto> entityDtos = entitiesResource.getEntities(KIND_NAME, null, null);

        //then
        assertEquals(2, entityDtos.size());

        Entity entity1 = EntityMapper.mapDtoToEntity(entityDtos.get(0));
        Entity entity2 = EntityMapper.mapDtoToEntity(entityDtos.get(1));

        assertEquals(A_NAME, entity1.getProperty(PROPERTY_NAME));
        assertEquals(ANOTHER_NAME, entity2.getProperty(PROPERTY_NAME));
    }
}
