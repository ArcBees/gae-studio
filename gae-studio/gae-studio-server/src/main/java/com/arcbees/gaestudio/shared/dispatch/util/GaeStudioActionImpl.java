/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.shared.dispatch.util;

import com.gwtplatform.dispatch.shared.Action;
import com.gwtplatform.dispatch.shared.Result;

/**
 * Base abstract implementation of {@link com.gwtplatform.dispatch.shared.Action}.
 *
 * @param <R> The {@link com.gwtplatform.dispatch.shared.Result} type returned.
 */
public abstract class GaeStudioActionImpl<R extends Result> implements Action<R> {
    public static final String GAE_STUDIO = "gae-studio/";

    @Override
    public boolean equals(Object obj) {
        return this.getClass().equals(obj.getClass());
    }

    @Override
    public String getServiceName() {
        String className = this.getClass().getName();
        int namePos = className.lastIndexOf(".") + 1;
        className = GAE_STUDIO + com.gwtplatform.dispatch.shared.ActionImpl.DEFAULT_SERVICE_NAME
                + className.substring(namePos);

        return className;
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }

    @Override
    public boolean isSecured() {
        return false;
    }
}
