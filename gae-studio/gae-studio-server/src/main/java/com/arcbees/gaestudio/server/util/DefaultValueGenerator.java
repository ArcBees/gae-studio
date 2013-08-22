/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.util;

import com.google.common.base.Defaults;

public class DefaultValueGenerator {
    public Object generate(Object value) throws IllegalAccessException, InstantiationException {
        if (value != null) {
            Class<?> clazz = value.getClass();

            if (clazz.isPrimitive()) {
                return Defaults.defaultValue(clazz);
            } else {
                Object defaultValue = defaultValue(clazz.getSimpleName());
                if (defaultValue == null) {
                    defaultValue = clazz.newInstance();
                }

                return defaultValue;
            }
        }

        return null;
    }

    private Object defaultValue(String className) {
        if (className.equals("Boolean")) {
            return false;
        } else if (className.equals("Long")) {
            return 0;
        } else if (className.equals("String")) {
            return "";
        } else if (className.equals("Byte")) {
            return 0;
        } else if (className.equals("Short")) {
            return 0;
        } else if (className.equals("Integer")) {
            return 0;
        } else if (className.equals("Float")) {
            return 0;
        } else if (className.equals("Double")) {
            return 0;
        } else {
            return null;
        }
    }
}
