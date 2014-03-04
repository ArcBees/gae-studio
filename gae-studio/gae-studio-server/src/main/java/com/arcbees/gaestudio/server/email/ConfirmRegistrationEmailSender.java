package com.arcbees.gaestudio.server.email;

import com.arcbees.gaestudio.server.service.MessageService;
import com.google.inject.Inject;

public class ConfirmRegistrationEmailSender {
    private static final String SUBJECT = "GAE Studio - Confirm your registration";

    private final ConfirmRegistrationEmailGenerator confirmRegistrationEmailGenerator;
    private final MessageService messageService;

    @Inject
    ConfirmRegistrationEmailSender(MessageService messageService,
                                   ConfirmRegistrationEmailGenerator confirmRegistrationEmailGenerator) {

        this.confirmRegistrationEmailGenerator = confirmRegistrationEmailGenerator;
        this.messageService = messageService;
    }

    public void sendEmail(String emailAddress, String tokenId, String redirectionUri) {
        String body = confirmRegistrationEmailGenerator.generateBody(tokenId, redirectionUri);

        messageService.sendEmail(emailAddress, SUBJECT, body);
    }
}
