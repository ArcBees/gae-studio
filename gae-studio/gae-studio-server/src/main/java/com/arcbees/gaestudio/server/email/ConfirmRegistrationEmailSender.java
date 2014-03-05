package com.arcbees.gaestudio.server.email;

import com.arcbees.appengine.mail.EmailSender;
import com.arcbees.gaestudio.server.service.mail.MessageService;
import com.google.inject.Inject;

public class ConfirmRegistrationEmailSender {
    private static final String SUBJECT = "GAE Studio - Confirm your registration";

    private final ConfirmRegistrationEmailBuilder confirmRegistrationEmailBuilder;
    private final EmailMessageGenerator emailMessageGenerator;
    private final EmailSender emailSender;
    private final MessageService messageService;

    @Inject
    ConfirmRegistrationEmailSender(MessageService messageService,
                                   EmailMessageGenerator emailMessageGenerator,
                                   EmailSender emailSender,
                                   ConfirmRegistrationEmailBuilder confirmRegistrationEmailBuilder) {

        this.confirmRegistrationEmailBuilder = confirmRegistrationEmailBuilder;
        this.emailMessageGenerator = emailMessageGenerator;
        this.messageService = messageService;
        this.emailSender = emailSender;
    }

    public void sendEmail(String emailAddress, String tokenId, String redirectionUri) {
        String body = confirmRegistrationEmailBuilder.generateBody(tokenId, redirectionUri);
        String message = emailMessageGenerator.generateBody(SUBJECT, body);

        messageService.sendEmail(emailSender, emailAddress, SUBJECT, message);
    }
}
