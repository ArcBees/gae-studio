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

import javax.inject.Inject;

import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(JukitoRunner.class)
public class DefaultValueGeneratorTest {
    @Inject
    DefaultValueGenerator defaultValueGenerator;

    @Test
    public void givenLongValue_generateDefault_shouldReturn0() {
        // given
        Long longValue = 3L;
        long primitiveLong = 4L;

        // when
        Object result = defaultValueGenerator.generate(longValue);
        Object primitiveResult = defaultValueGenerator.generate(primitiveLong);

        // then
        assertEquals(0L, result);
        assertEquals(0L, primitiveResult);
    }

    @Test
    public void givenStringValue_generateDefault_shouldReturnEmptyString() {
        // given
        String stringValue = "hello";

        // when
        Object result = defaultValueGenerator.generate(stringValue);

        // then
        assertEquals("", result);
    }

    @Test
    public void givenByteValue_generateDefault_shouldReturn0() {
        // given
        Byte byteValue = 3;
        byte primitiveByte = 4;

        // when
        Object result = defaultValueGenerator.generate(byteValue);
        Object primitiveResult = defaultValueGenerator.generate(primitiveByte);

        // then
        assertEquals((byte) 0, result);
        assertEquals((byte) 0, primitiveResult);
    }

    @Test
    public void givenShortValue_generateDefault_shouldReturn0() {
        // given
        Short shortValue = 3;
        short primitiveShort = 4;

        // when
        Object result = defaultValueGenerator.generate(shortValue);
        Object primitiveResult = defaultValueGenerator.generate(primitiveShort);

        // then
        assertEquals((short) 0, result);
        assertEquals((short) 0, primitiveResult);
    }

    @Test
    public void givenIntegerValue_generateDefault_shouldReturn0() {
        // given
        Integer integerValue = 3;
        int primitiveInteger = 4;

        // when
        Object result = defaultValueGenerator.generate(integerValue);
        Object primitiveResult = defaultValueGenerator.generate(primitiveInteger);

        // then
        assertEquals(0, result);
        assertEquals(0, primitiveResult);
    }

    @Test
    public void givenFloatValue_generateDefault_shouldReturn0() {
        // given
        Float floatValue = 3f;
        float primitiveFloat = 4;

        // when
        Object result = defaultValueGenerator.generate(floatValue);
        Object primitiveResult = defaultValueGenerator.generate(primitiveFloat);

        // then
        assertEquals(0f, result);
        assertEquals(0f, primitiveResult);
    }

    @Test
    public void givenDoubleValue_generateDefault_shouldReturn0() {
        // given
        Double doubleValue = 3d;
        double primitiveDouble = 4;

        // when
        Object result = defaultValueGenerator.generate(doubleValue);
        Object primitiveResult = defaultValueGenerator.generate(primitiveDouble);

        // then
        assertEquals(0d, result);
        assertEquals(0d, primitiveResult);
    }
}
