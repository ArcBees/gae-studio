import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.labs.repackaged.com.google.common.collect.Lists;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.gson.Gson;

/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

public class Main {
    public static final String HOST = "https://gaestudio-demo.appspot.com";

    public static void main(String[] args) throws IOException, URISyntaxException {
        createFile();

        String clientId = UUID.randomUUID().toString();
        UploadEndListener uploadEndListener = new UploadEndListener(clientId);
        uploadEndListener.listen();

        FileSender fileSender = new FileSender(clientId);
        fileSender.send();
    }

    private static void createFile() {
        LocalServiceTestHelper helper =
                new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
        helper.setUp();

        long beginTime = System.nanoTime();
        List<Entity> entities = new ArrayList<Entity>();
        for (int i = 1; i <= 90000; i++) {
            Entity entity = new Entity("Blah", i);
            entity.setProperty("blah", 1);
            entity.setProperty("name", "bonjour");
            entities.add(entity);
        }
        DatePrinter.printDate(beginTime, System.nanoTime());

        System.out.println();


        Gson gson = new Gson();

        String json = gson.toJson(entities);
        PrintWriter printWriter = null;
        try {
             printWriter = new PrintWriter("lol.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        printWriter.println(json);
        printWriter.close();

        helper.tearDown();
    }
}
