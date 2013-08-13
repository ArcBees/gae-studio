package com.arcbees.gaestudio.server.rest;

import java.util.List;

import javax.inject.Inject;

import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.arcbees.gaestudio.shared.DeleteEntities;
import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.arcbees.gaestudio.testutil.FakeEntity;
import com.arcbees.gaestudio.testutil.FakeEntityDao;
import com.arcbees.gaestudio.testutil.GaeTestBase;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.GsonDatastoreFactory;
import com.google.gson.Gson;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(JukitoRunner.class)
public class EntitiesResourceTest extends GaeTestBase {
    private static final String KIND_NAME = "FakeEntity";
    private static final String A_NAME = "a-name";
    private static final String A_NICKNAME = "a-nickname";
    private static final String ANOTHER_NAME = "another-name";
    private static final String ANOTHER_NICKNAME = "another-nickname";

    @Inject
    EntitiesResource entitiesResource;
    @Inject
    FakeEntityDao fakeEntityDao;


    @Test
    public void entityStored_createEmptyEntity_shouldReturnEmptyEntity() {
        //given
        fakeEntityDao.put(new FakeEntity(A_NAME, A_NICKNAME));

        //when
        EntityDto entityDto = entitiesResource.createEmptyEntity(KIND_NAME);

        //then
        Entity entity = convertToEntity(entityDto);

        assertNotNull(entity);
        assertEquals(KIND_NAME, entity.getKind());
        assertEquals("", entity.getProperty("name"));
        assertEquals("", entity.getProperty("nickname"));
    }

    @Test
    public void twoEntitiesStored_getCount_shouldReturnTwoEntities() {
        //given
        fakeEntityDao.put(new FakeEntity(A_NAME, A_NICKNAME));
        fakeEntityDao.put(new FakeEntity(ANOTHER_NAME, ANOTHER_NICKNAME));

        //when
        Integer entityCount = entitiesResource.getCount(KIND_NAME);

        //then
        assertEquals(2l, (long) entityCount);
    }

    @Test
    public void twoEntitiesStored_deleteEntities_shouldHaveNoMoreEntities() {
        //given
        fakeEntityDao.put(new FakeEntity(A_NAME, A_NICKNAME));
        fakeEntityDao.put(new FakeEntity(ANOTHER_NAME, ANOTHER_NICKNAME));

        //when
        entitiesResource.deleteEntities(KIND_NAME, null, DeleteEntities.KIND);

        //then
        assertEquals(0l, (long) entitiesResource.getCount(KIND_NAME));
    }

    @Test
    public void twoEntitiesStored_getEntities_shouldReturnTwoEntities() {
        //given
        fakeEntityDao.put(new FakeEntity(A_NAME, A_NICKNAME));
        fakeEntityDao.put(new FakeEntity(ANOTHER_NAME, ANOTHER_NICKNAME));

        //when
        List<EntityDto> entityDtos = entitiesResource.getEntities(KIND_NAME, null, null);

        //then
        assertEquals(2, entityDtos.size());

        Entity entity1 = convertToEntity(entityDtos.get(0));
        Entity entity2 = convertToEntity(entityDtos.get(1));

        assertEquals(A_NAME, entity1.getProperty("name"));
        assertEquals(A_NICKNAME, entity1.getProperty("nickname"));
        assertEquals(ANOTHER_NAME, entity2.getProperty("name"));
        assertEquals(ANOTHER_NICKNAME, entity2.getProperty("nickname"));
    }

    private Entity convertToEntity(EntityDto entityDto) {
        Gson gson = GsonDatastoreFactory.create();

        return gson.fromJson(entityDto.getJson(), Entity.class);
    }
}
