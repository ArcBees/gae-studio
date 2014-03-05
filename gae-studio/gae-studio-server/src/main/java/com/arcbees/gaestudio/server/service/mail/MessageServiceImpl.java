package com.arcbees.gaestudio.server.service.mail;

import javax.inject.Inject;

import com.arcbees.appengine.mail.Email;
import com.arcbees.appengine.mail.EmailBuilder;
import com.arcbees.appengine.mail.EmailSender;
import com.arcbees.gaestudio.server.GaeStudioConstants;

public class MessageServiceImpl implements MessageService {
    @Inject
    EmailSender emailSender;

    public void sendEmail(String emailAddress, String subject, String message) {
        Email email = EmailBuilder.to(emailAddress)
                .fromAddress(GaeStudioConstants.ARCBEES_MAIL_SENDER)
                .fromPersonal("GAE Studio")
                .body(message)
                .subject(subject)
                .build();

        emailSender.send(email);
    }
}
