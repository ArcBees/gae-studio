/**
 * Copyright (c) 2013 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.shared.rest;

public class EndPoints {
    public static final String REST_PATH = "gae-studio/";

    public static final String ID = "{" + UrlParameters.ID + "}/";

    public static final String BLOBS = REST_PATH + "blobs/";
    public static final String ENTITIES = REST_PATH + "entities/";
    public static final String NAMESPACES = REST_PATH + "namespaces/";
    public static final String KINDS = REST_PATH + "kinds/";
    public static final String RECORD = REST_PATH + "record/";
    public static final String OPERATIONS = REST_PATH + "operations/";

    public static final String COUNT = "count/";

    public static final String AUTH = REST_PATH + "auth/";
    public static final String REGISTER = "register/";
    public static final String LOGIN = "login/";
    public static final String RESET_PASSWORD = "resetpassword/";
    public static final String CHECK = "check/";
    public static final String ARCBEES_LICENSE_SERVICE = "https://arcbees-license-service.appspot.com/key/";
}
