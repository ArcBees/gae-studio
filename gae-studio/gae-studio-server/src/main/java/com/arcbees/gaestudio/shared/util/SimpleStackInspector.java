/**
 * Copyright 2015 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
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
