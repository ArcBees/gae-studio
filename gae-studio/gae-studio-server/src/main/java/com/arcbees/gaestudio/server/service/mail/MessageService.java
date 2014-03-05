package com.arcbees.gaestudio.server.service.mail;

import com.arcbees.appengine.mail.EmailSender;

public interface MessageService {
    void sendEmail(EmailSender emailSender, String emailAddress, String subject, String message);
}
