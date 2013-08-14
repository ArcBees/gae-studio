package com.arcbees.gaestudio.testutil;

import org.junit.After;
import org.junit.Before;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalSearchServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class GaeTestBase {
    private static final LocalServiceTestHelper helper = new LocalServiceTestHelper(new
            LocalDatastoreServiceTestConfig(), new LocalSearchServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }
}
