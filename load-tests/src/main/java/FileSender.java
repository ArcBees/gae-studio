import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

public class FileSender {
    private final String clientId;

    public FileSender(String clientId) {
        this.clientId = clientId;
    }

    public void send() {
        UploadUrlCreator uploadUrlCreator = new UploadUrlCreator();
        UploadUrl uploadUrl = null;
        try {
            uploadUrl = uploadUrlCreator.getUploadUrl();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(uploadUrl.value);
//        File file = new File("/Users/spgingras/gaestudio/load-tests/src/main/java/Player.json");
        File file = new File("lol.json");

        HttpEntity multipartEntity = null;
        try {
            multipartEntity = MultipartEntityBuilder.create().addPart("fileUpload",
                    new FileBody(file)).addPart("clientId", new StringBody(clientId)).build();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        request.setEntity(multipartEntity);

        try {
            httpClient.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
