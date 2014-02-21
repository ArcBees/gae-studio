package com.arcbees.gaestudio.server.util;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PackageHelperTest {
    @Test
    public void testGetLeafPackageName() throws Exception {
        Package aPackage = Package.getPackage("com.arcbees.gaestudio.server.util");

        String leafPackageName = PackageHelper.getLeafPackageName(aPackage);

        assertEquals("util", leafPackageName);
    }
}
