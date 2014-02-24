/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

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
