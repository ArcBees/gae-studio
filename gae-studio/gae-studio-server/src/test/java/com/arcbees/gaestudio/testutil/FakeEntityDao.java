package com.arcbees.gaestudio.testutil;

import static com.arcbees.gaestudio.testutil.OfyService.ofy;

public class FakeEntityDao {
    public FakeEntity put(FakeEntity fakeEntity) {
        ofy().save().entity(fakeEntity).now();

        return fakeEntity;
    }
}
