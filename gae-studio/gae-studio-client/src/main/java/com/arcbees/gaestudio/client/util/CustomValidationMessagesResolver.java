package com.arcbees.gaestudio.client.util;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.ConstantsWithLookup;
import com.google.gwt.validation.client.AbstractValidationMessageResolver;
import com.google.gwt.validation.client.UserValidationMessagesResolver;

public class CustomValidationMessagesResolver extends AbstractValidationMessageResolver implements
        UserValidationMessagesResolver {
    public CustomValidationMessagesResolver() {
        super((ConstantsWithLookup) GWT.create(GwtValidationMessages.class));
    }
}
