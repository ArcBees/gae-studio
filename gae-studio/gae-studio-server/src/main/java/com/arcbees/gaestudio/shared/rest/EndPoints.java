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

package com.arcbees.gaestudio.shared.rest;

public class EndPoints {
    public static final String REST_PATH = "gae-studio/";
    public static final String ID = "{" + UrlParameters.ID + "}/";
    public static final String BLOBS = REST_PATH + "blobs/";
    public static final String ENTITIES = REST_PATH + "entities/";
    public static final String ENTITY = REST_PATH + "entity/";
    public static final String NAMESPACES = REST_PATH + "namespaces/";
    public static final String KINDS = REST_PATH + "kinds/";
    public static final String RECORD = REST_PATH + "record/";
    public static final String CHANNEL = REST_PATH + "channel/";
    public static final String COUNT = "count/";
    public static final String AUTH = REST_PATH + "auth/";
    public static final String REGISTER = "register/";
    public static final String LOGIN = "login/";
    public static final String RESET_PASSWORD = "resetpassword/";
    public static final String CHECK = "check/";
    public static final String ARCBEES_LICENSE_SERVICE = "https://arcbees-license-service.appspot.com/key/";
    public static final String TOKEN = "token/";
    public static final String EXPORT_JSON = REST_PATH + "exportjson/";
    public static final String EXPORT_CSV = REST_PATH + "exportcsv/";
    public static final String IMPORT = REST_PATH + "import/";
    public static final String GQL = REST_PATH + "gql/";
    public static final String TASK = "task/";
    public static final String IMPORT_TASK = IMPORT + TASK;
    public static final String ARCBEES_MAIL_SERVICE = "https://arcbees-mail-service.appspot.com/mail";
}
