/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
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
public class RatingValidatorTest {
    public static class MyModule extends JukitoModule {
        @Override
        protected void configureTest() {
            Integer delta = 1;

            bindManyInstances(TestCase.class,
                    new TestCase(0, true),
                    new TestCase(0 + delta, true),
                    new TestCase(0 - delta, false),
                    new TestCase(100, true),
                    new TestCase(100 + delta, false),
                    new TestCase(100 - delta, true));
        }
    }

    public static class TestCase {
        Long value;
        Boolean isValid;

        public TestCase(Integer value, Boolean valid) {
            this.value = (long) value;
            isValid = valid;
        }
    }

    private final RatingValidator ratingValidator = new RatingValidator();

    @Test
    public void isRatingValid(@All TestCase testCase) {
        assertEquals("Value: " + testCase.value, testCase.isValid, ratingValidator.isRatingValid(testCase.value));
    }
}
