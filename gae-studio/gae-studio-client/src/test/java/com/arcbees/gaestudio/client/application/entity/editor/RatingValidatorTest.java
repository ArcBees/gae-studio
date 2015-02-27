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

package com.arcbees.gaestudio.client.application.entity.editor;

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
