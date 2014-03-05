package com.arcbees.gaestudio.server.email;

import javax.inject.Inject;

import com.arcbees.appengine.mail.EmailSender;
import com.arcbees.gaestudio.server.service.mail.MessageService;

public class ResetPasswordEmailSender {
    private static final String SUBJECT = "ArcBees - Reset your password";

    private final ResetPasswordEmailBuilder resetPasswordEmailBuilder;
    private final EmailMessageGenerator emailMessageGenerator;
    private final EmailSender emailSender;
    private final MessageService messageService;

    @Inject
    ResetPasswordEmailSender(MessageService messageService,
                             EmailMessageGenerator emailMessageGenerator,
                             EmailSender emailSender,
                             ResetPasswordEmailBuilder resetPasswordEmailBuilder) {

        this.resetPasswordEmailBuilder = resetPasswordEmailBuilder;
        this.emailMessageGenerator = emailMessageGenerator;
        this.messageService = messageService;
        this.emailSender = emailSender;
    }

    public void sendEmail(String emailAddress, String token) {
        String body = resetPasswordEmailBuilder.generateBody(emailAddress, token);
        String message = emailMessageGenerator.generateBody(SUBJECT, body);

        messageService.sendEmail(emailSender, emailAddress, SUBJECT, message);
    }
}
