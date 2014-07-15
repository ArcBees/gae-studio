/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
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
