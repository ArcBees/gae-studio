/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.util;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.impl.FormPanelImpl;
import com.google.gwt.user.client.ui.impl.FormPanelImplHost;

public class WindowNameFormPanel extends FormPanelImpl {
    @Override
    public native String getContents(Element iframe) /*-{
        try {
            if (!iframe.contentWindow || !iframe.contentWindow.document)
                return null;

            return iframe.contentWindow.name;
        } catch (e) {
            return null;
        }
    }-*/;

    @Override
    public native void hookEvents(Element iframe, Element form,
                                  FormPanelImplHost listener) /*-{
        if (iframe) {
            iframe.onload = function () {
                if (!iframe.__formAction)
                    return;

                if (!iframe.__restoreSameDomain) {
                    iframe.__restoreSameDomain = true;
                    iframe.contentWindow.location =
                        @com.google.gwt.core.client.GWT::getModuleBaseURL()() + "clear.cache.gif";
                    return;
                }
                listener.@com.google.gwt.user.client.ui.impl.FormPanelImplHost::onFrameLoad()();
            };
        }

        form.onsubmit = function () {
            if (iframe) {
                iframe.__formAction = form.action;
                iframe.__restoreSameDomain = false;
            }
            return listener.@com.google.gwt.user.client.ui.impl.FormPanelImplHost::onFormSubmit()();
        };
    }-*/;
}
