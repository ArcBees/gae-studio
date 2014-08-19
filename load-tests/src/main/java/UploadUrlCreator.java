import java.io.IOException;

import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;

import com.google.gson.Gson;

/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

public class UploadUrlCreator {
    private final Gson gson = new Gson();

    public UploadUrl getUploadUrl() throws IOException {

        Response response = Request.Get(Main.HOST + "/rest/gae-studio/import").addHeader("Accept",
                "application/json").execute();

        String string = response.returnContent().asString();

        return gson.fromJson(string, UploadUrl.class);
    }
}
