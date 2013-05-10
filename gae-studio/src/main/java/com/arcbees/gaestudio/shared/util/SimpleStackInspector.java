/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.shared.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SimpleStackInspector implements StackInspector {
    // TODO support wildcards, also externalize to configuration
    private static final String _IGNORED_PACKAGES[] = {
            "java.lang",
            "com.arcbees.gaestudio.server.recorder",
            "com.google.appengine.api.datastore",
            "com.google.appengine.api.utils",
            "com.googlecode.objectify.impl",
            "com.googlecode.objectify.util",
            "com.vercer.engine.persist.standard"
    };

    private static final Set<String> IGNORED_PACKAGES = new HashSet<String>(_IGNORED_PACKAGES.length);

    static {
        Collections.addAll(IGNORED_PACKAGES, _IGNORED_PACKAGES);
    }

    @Override
    public StackTraceElement getCaller(StackTraceElement[] stackTrace) {
        for (StackTraceElement currentElement : stackTrace) {
            String packageName = getStackTraceElementPackage(currentElement);
            if (!IGNORED_PACKAGES.contains(packageName)) {
                return currentElement;
            }
        }

        return null;
    }

    private String getStackTraceElementPackage(StackTraceElement currentElement) {
        String className = currentElement.getClassName();
        int lastDotIndex = className.lastIndexOf('.');
        return lastDotIndex != -1 ? className.substring(0, lastDotIndex) : "";
    }
}
