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

import javax.inject.Singleton;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONValue;

@Singleton
public class JsonUtils {
    JsonUtils() {
        injectFunction();
    }

    public boolean compareObjects(JSONValue o1, JSONValue o2) {
        if (o1.equals(o2) || String.valueOf(o1).equals(String.valueOf(o2))) {
            return true;
        } else if (o1.isObject() != null && o2.isObject() != null) {
            return compareJsonObjects(o1.isObject().getJavaScriptObject(), o2.isObject().getJavaScriptObject());
        } else {
            return false;
        }
    }

    private native boolean compareJsonObjects(JavaScriptObject o1, JavaScriptObject o2) /*-{
        return $wnd.compareJsonObjects(o1, o2);
    }-*/;

    private native void injectFunction() /*-{
        $wnd.compareJsonObjects = function (x, y) {
            var leftChain = [], rightChain = [];

            function doCompareObjects(x, y) {
                var p;

                if (x === y) {
                    return true;
                }

                if (!(x instanceof Object && y instanceof Object)) {
                    return false;
                }

                if (x.isPrototypeOf(y) || y.isPrototypeOf(x)) {
                    return false;
                }

                if (x.prototype !== y.prototype) {
                    return false;
                }

                // check for loops
                if (leftChain.indexOf(x) > -1 || rightChain.indexOf(y) > -1) {
                    return false;
                }

                for (p in y) {
                    if (y.hasOwnProperty(p) !== x.hasOwnProperty(p)) {
                        return false;
                    } else if (typeof y[p] !== typeof x[p]) {
                        return false;
                    }
                }

                for (p in x) {
                    if (y.hasOwnProperty(p) !== x.hasOwnProperty(p)) {
                        return false;
                    } else if (typeof y[p] !== typeof x[p]) {
                        return false;
                    }

                    switch (typeof (x[p])) {
                        case 'object':
                        case 'function':
                            leftChain.push(x);
                            rightChain.push(y);

                            if (!doCompareObjects(x[p], y[p])) {
                                return false;
                            }

                            leftChain.pop();
                            rightChain.pop();
                            break;
                        default:
                            if (x[p] !== y[p]) {
                                return false;
                            }
                            break;
                    }
                }

                return true;
            }

            return doCompareObjects(x, y);
        }
    }-*/;
}
