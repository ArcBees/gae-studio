import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import edu.gvsu.cis.masl.channelAPI.ChannelAPI;

/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

public class UploadEndListener implements GaeStudioTaskListener.ImportCompletedListener {
    private final String clientId;
    private long beginTime;
    private ChannelAPI channel;

    public UploadEndListener(String clientId) {
        this.clientId = clientId;
    }

    public void listen() {
        beginTime = System.nanoTime();
        ChannelTokenCreator channelTokenCreator = new ChannelTokenCreator();
        String channelToken = null;
        try {
            channelToken = channelTokenCreator.getChannelToken(clientId);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        GaeStudioTaskListener listener = new GaeStudioTaskListener(this);

        try {
            channel = new ChannelAPI(Main.HOST, channelToken, channelToken, listener);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            channel.open();
        } catch (ChannelAPI.ChannelException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onImportCompleted() {
        DatePrinter.printDate(beginTime, System.nanoTime());
        try {
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
