/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.visualizer.widget.entity;

import org.jukito.All;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(JukitoRunner.class)
public class GeoPtValidatorTest {
    public static class MyModule extends JukitoModule {
        @Override
        protected void configureTest() {
            Double delta = 0.000000000001;

            bindManyNamedInstances(TestCase.class, "latitudes",
                    new TestCase(90d, true),
                    new TestCase(90d + delta, false),
                    new TestCase(90d - delta, true),
                    new TestCase(-90d, true),
                    new TestCase(-90d + delta, true),
                    new TestCase(-90d - delta, false));

            bindManyNamedInstances(TestCase.class, "longitudes",
                    new TestCase(180d, true),
                    new TestCase(180d + delta, false),
                    new TestCase(180d - delta, true),
                    new TestCase(-180d, true),
                    new TestCase(-180d + delta, true),
                    new TestCase(-180d - delta, false));
        }
    }

    public static class TestCase {
        Double value;
        Boolean isValid;

        public TestCase(Double value, Boolean valid) {
            this.value = value;
            isValid = valid;
        }
    }

    private final GeoPtValidator geoPtValidator = new GeoPtValidator();

    @Test
    public void isLatitudeValid(@All("latitudes") TestCase testCase) {
        assertEquals("Latitude: " + testCase.value, testCase.isValid, geoPtValidator.isLatitudeValid(testCase.value));
    }

    @Test
    public void isLongitudeValid(@All("longitudes") TestCase testCase) {
        assertEquals("Longitude: " + testCase.value, testCase.isValid, geoPtValidator.isLongitudeValid(testCase.value));
    }
}
