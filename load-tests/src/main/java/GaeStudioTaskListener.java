import com.google.gson.Gson;

import edu.gvsu.cis.masl.channelAPI.ChannelService;

/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

public class GaeStudioTaskListener implements ChannelService {
    public interface ImportCompletedListener {
        void onImportCompleted();
    }

    private final Gson gson = new Gson();
    private final ImportCompletedListener importCompletedListener;

    public GaeStudioTaskListener(
            ImportCompletedListener importCompletedListener) {
        this.importCompletedListener = importCompletedListener;
    }

    @Override
    public void onOpen() {
        System.out.println("Open");
    }

    @Override
    public void onMessage(String message) {
        ChannelMessage channelMessage = gson.fromJson(message, ChannelMessage.class);
        System.out.println("Server push: " + channelMessage);

        if ("IMPORT_COMPLETED".equals(channelMessage.topic)) {
            importCompletedListener.onImportCompleted();
        }
    }

    @Override
    public void onClose() {
        System.out.println("Close");
    }

    @Override
    public void onError(Integer errorCode, String description) {
        System.out.println("Error: " + errorCode + " " + description);
    }
}
