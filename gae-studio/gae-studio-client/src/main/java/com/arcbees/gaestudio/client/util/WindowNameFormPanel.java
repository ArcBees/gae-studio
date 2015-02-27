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

package com.arcbees.gaestudio.client.util;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.impl.FormPanelImpl;
import com.google.gwt.user.client.ui.impl.FormPanelImplHost;

/**
 * Class used to hack the same-origin policy when uploading to the blobstore from a different host than the Blobstore
 * upload url.
 */
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
