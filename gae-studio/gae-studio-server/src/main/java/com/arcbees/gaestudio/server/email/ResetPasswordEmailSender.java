package com.arcbees.gaestudio.server.email;

import javax.inject.Inject;

import com.arcbees.gaestudio.server.service.MessageService;

public class ResetPasswordEmailSender {
    private static final String SUBJECT = "ArcBees - Reset your password";

    private final ResetPasswordEmailBodyGenerator resetPasswordEmailBodyGenerator;
    private final MessageService messageService;

    @Inject
    ResetPasswordEmailSender(MessageService messageService,
                             ResetPasswordEmailBodyGenerator resetPasswordEmailBodyGenerator) {

        this.resetPasswordEmailBodyGenerator = resetPasswordEmailBodyGenerator;
        this.messageService = messageService;
    }

    public void sendEmail(String emailAddress, String token) {
        String body = resetPasswordEmailBodyGenerator.generateBody(emailAddress, token);

        messageService.sendEmail(emailAddress, SUBJECT, body);
    }
}
