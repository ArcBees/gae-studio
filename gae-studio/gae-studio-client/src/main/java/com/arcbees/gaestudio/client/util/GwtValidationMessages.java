package com.arcbees.gaestudio.client.util;

import com.arcbees.gaestudio.shared.util.Validation;
import com.google.gwt.i18n.client.ConstantsWithLookup;

public interface GwtValidationMessages extends ConstantsWithLookup {
    @Key(Validation.REQUIRED_KEY)
    String required();
}
