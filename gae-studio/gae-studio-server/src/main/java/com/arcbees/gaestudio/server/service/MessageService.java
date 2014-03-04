package com.arcbees.gaestudio.server.service;

public interface MessageService {
    void sendEmail(String emailAddress, String subject, String body);
}
