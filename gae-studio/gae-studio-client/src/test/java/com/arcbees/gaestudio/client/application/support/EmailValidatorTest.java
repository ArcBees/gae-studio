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

package com.arcbees.gaestudio.client.application.support;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmailValidatorTest {
    EmailValidator emailValidator = new EmailValidator();

    @Test
    public void isValid_valid() {
        assertTrue(emailValidator.isValid("simon.pierre.gingras@gmail.com", null));
    }

    @Test
    public void isValid_invalid() {
        assertFalse(emailValidator.isValid("simon.pierre.gingras@gmail.c", null));
    }

    @Test
    public void isValid_empty() {
        assertFalse(emailValidator.isValid("", null));
    }

    @Test
    public void isValid_null() {
        assertFalse(emailValidator.isValid(null, null));
    }
}
