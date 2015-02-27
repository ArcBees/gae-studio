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

package com.arcbees.gaestudio.server.exception;

import javax.inject.Singleton;

import com.google.inject.AbstractModule;

public class ExceptionModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(EntityNotFoundExceptionMapper.class).in(Singleton.class);
        bind(IllegalAccessExceptionMapper.class).in(Singleton.class);
        bind(InstantiationExceptionMapper.class).in(Singleton.class);
        bind(UserNotLoggedInExceptionMapper.class).in(Singleton.class);
        bind(InvalidGqlSyntaxExceptionMapper.class).in(Singleton.class);
    }
}
