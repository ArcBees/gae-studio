import java.util.concurrent.TimeUnit;

/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

public class DatePrinter {
    public static void printDate(long beginTime, long endTime) {
        System.out.println("begin: " + beginTime + ", end: " + endTime);
        long l = endTime - beginTime;

        final long hr = TimeUnit.NANOSECONDS.toHours(l);
        final long min = TimeUnit.NANOSECONDS.toMinutes(l - TimeUnit.HOURS.toMillis(hr));
        final long sec = TimeUnit.NANOSECONDS.toSeconds(l - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min));
        final long ms = TimeUnit.NANOSECONDS.toMillis(l - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min) - TimeUnit.SECONDS.toMillis(sec));

        System.out.println(String.format("%02d:%02d:%02d.%03d", hr, min, sec, ms));
    }
}
