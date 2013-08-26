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
    public Object generate(Object value) {
        if (value != null) {
            Class<?> clazz = value.getClass();

            if (clazz.isPrimitive()) {
                return Defaults.defaultValue(clazz);
            } else {
                Object defaultValue = BoxedDefaults.defaultValue(clazz);
                if (defaultValue == null) {
                    try {
                        defaultValue = clazz.newInstance();
                    } catch (InstantiationException e) {
                        return null;
                    } catch (IllegalAccessException e) {
                        return null;
                    }
                }

                return defaultValue;
            }
        }

        return null;
    }
}
