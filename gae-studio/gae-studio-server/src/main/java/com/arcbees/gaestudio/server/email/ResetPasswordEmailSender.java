package com.arcbees.gaestudio.server.email;

import javax.inject.Inject;

import com.arcbees.gaestudio.server.service.mail.MessageService;

public class ResetPasswordEmailSender {
    private static final String SUBJECT = "ArcBees - Reset your password";

    private final ResetPasswordEmailBuilder resetPasswordEmailBuilder;
    private final EmailMessageGenerator emailMessageGenerator;
    private final MessageService messageService;

    @Inject
    ResetPasswordEmailSender(MessageService messageService,
                             EmailMessageGenerator emailMessageGenerator,
                             ResetPasswordEmailBuilder resetPasswordEmailBuilder) {

        this.resetPasswordEmailBuilder = resetPasswordEmailBuilder;
        this.emailMessageGenerator = emailMessageGenerator;
        this.messageService = messageService;
    }

    public void sendPasswordEmail(String emailAddress, String token) {
        String body = resetPasswordEmailBuilder.generateBody(emailAddress, token);
        String message = emailMessageGenerator.generateBody(SUBJECT, body);

        messageService.sendEmail(emailAddress, SUBJECT, message);
    }
}
