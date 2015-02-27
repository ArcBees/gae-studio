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
