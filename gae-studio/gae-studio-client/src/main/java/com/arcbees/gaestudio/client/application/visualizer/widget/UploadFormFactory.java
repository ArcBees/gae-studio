package com.arcbees.gaestudio.client.application.visualizer.widget;

public interface UploadFormFactory {
    UploadForm createForm(String uploadUrl,
                          UploadForm.Handler handler);
}
