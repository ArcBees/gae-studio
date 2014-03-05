package com.arcbees.gaestudio.server.service.mail;

public interface MessageService {
    void sendEmail(String emailAddress, String subject, String message);
}
