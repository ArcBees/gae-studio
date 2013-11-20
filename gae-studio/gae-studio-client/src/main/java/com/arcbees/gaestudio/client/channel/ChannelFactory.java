/*
 * Copyright (C) 2011 Google Inc.
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

package com.arcbees.gaestudio.client.channel;

import com.google.inject.ImplementedBy;

/**
 * Creates new {@link Channel}s.
 * <p/>
 * The App Engine Channel API script must be installed before the GWT application, per the
 * instructions at http://code.google.com/appengine/docs/java/channel/javascript.html
 */
@ImplementedBy(ChannelFactoryImpl.class)
public interface ChannelFactory {

    /**
     * Creates a new {@code Channel} object with the given token. The token must be a valid Channel
     * API token created by App Engine's channel service.
     *
     * @param token
     * @return a new {@code Channel}
     */
    Channel createChannel(String token);
}
