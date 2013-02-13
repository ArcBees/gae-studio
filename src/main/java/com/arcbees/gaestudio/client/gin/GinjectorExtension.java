package com.arcbees.gaestudio.client.gin;

import com.arcbees.gaestudio.client.resources.AppConstants;
import com.arcbees.gaestudio.client.resources.AppMessages;

public interface GinjectorExtension {
    AppConstants getAppConstants();
    AppMessages getAppMessages();
}