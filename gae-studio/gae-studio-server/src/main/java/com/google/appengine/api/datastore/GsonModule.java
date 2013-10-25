/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.google.appengine.api.datastore;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;

import javax.inject.Singleton;

import com.arcbees.gaestudio.shared.Constants;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Entity.UnindexedValue;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.storage.onestore.v3.OnestoreEntity;

public class GsonModule extends AbstractModule {
    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    Gson getGson() {
        ExclusionStrategy entityProtoExclusionStrategy = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getDeclaredClass().equals(OnestoreEntity.EntityProto.class);
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        };

        GsonBuilder gsonBuilder = new GsonBuilder()
                .setPrettyPrinting()
                .setDateFormat(Constants.JSON_DATE_FORMAT)
                .serializeNulls()
                .excludeFieldsWithModifiers(Modifier.STATIC)
                .setExclusionStrategies(entityProtoExclusionStrategy);

        // Instance creators
        gsonBuilder.registerTypeAdapter(Entity.class, new EntityInstanceCreator())
                   .registerTypeAdapter(AppIdNamespace.class, new AppIdNamespaceInstanceCreator())
                   .registerTypeAdapter(Key.class, new KeyInstanceCreator());

        // Type Adapters
        gsonBuilder.registerTypeAdapter(BlobKey.class, new BlobKeyValueAdapter())
                   .registerTypeAdapter(Blob.class, new BlobValueAdapter())
                   .registerTypeAdapter(Category.class, new CategoryValueAdapter())
                   .registerTypeAdapter(Collection.class, new CollectionValueAdapter())
                   .registerTypeAdapter(Email.class, new EmailValueAdapter())
                   .registerTypeAdapter(IMHandle.class, new IMHandleDeserializer())
                   .registerTypeAdapter(Link.class, new LinkValueAdapter())
                   .registerTypeAdapter(Map.class, new PropertiesValueAdapter())
                   .registerTypeAdapter(PhoneNumber.class, new PhoneNumberValueAdapter())
                   .registerTypeAdapter(PostalAddress.class, new PostalAddressValueAdapter())
                   .registerTypeAdapter(PropertyValue.class, new PropertyValueAdapter())
                   .registerTypeAdapter(Rating.class, new RatingValueAdapter())
                   .registerTypeAdapter(ShortBlob.class, new ShortBlobValueAdapter())
                   .registerTypeAdapter(Text.class, new TextValueAdapter())
                   .registerTypeAdapter(UnindexedValue.class, new UnindexedValueAdapter());

        return gsonBuilder.create();
    }
}
