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

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.google.common.base.Strings;

public class EmailValidator implements ConstraintValidator<Email, String> {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(?:[a-zA-Z]{2,6})$";

    public void initialize(Email annotation) {
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Strings.nullToEmpty(value).matches(EMAIL_PATTERN);
    }
}
