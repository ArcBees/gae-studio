/*
 * Copyright 2012 ArcBees Inc. All rights reserved.
 */

package com.arcbees.gaestudio.client.application.profiler.request;

import com.google.inject.assistedinject.Assisted;

public interface RequestElementFactory {
    RequestElement create(@Assisted final RequestStatistics requestStatistics,
                          @Assisted final RequestElementCallback callback);
}
