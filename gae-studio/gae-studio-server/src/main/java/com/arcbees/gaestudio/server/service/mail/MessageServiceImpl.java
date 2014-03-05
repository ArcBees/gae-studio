package com.arcbees.gaestudio.server.service.mail;

import com.arcbees.appengine.mail.Email;
import com.arcbees.appengine.mail.EmailBuilder;
import com.arcbees.appengine.mail.EmailSender;
import com.arcbees.gaestudio.server.GaeStudioConstants;
import com.google.inject.Inject;

public class MessageServiceImpl implements MessageService {
    private final EmailSender emailSender;

    @Inject
    MessageServiceImpl(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

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
