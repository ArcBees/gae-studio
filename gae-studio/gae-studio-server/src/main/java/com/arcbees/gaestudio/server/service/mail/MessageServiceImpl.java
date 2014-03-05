package com.arcbees.gaestudio.server.service.mail;

import com.arcbees.appengine.mail.Email;
import com.arcbees.appengine.mail.EmailBuilder;
import com.arcbees.appengine.mail.EmailSender;
import com.arcbees.gaestudio.server.GaeStudioConstants;
import com.arcbees.gaestudio.server.email.ConfirmRegistrationEmailBuilder;
import com.arcbees.gaestudio.server.email.EmailMessageGenerator;
import com.arcbees.gaestudio.server.email.ResetPasswordEmailBuilder;
import com.google.inject.Inject;

public class MessageServiceImpl implements MessageService {
    private static final String CONFIRM_SUBJECT = "GAE Studio - Confirm your registration";
    private static final String RESET_PASSWORD_SUBJECT = "ArcBees - Reset your password";

    private final EmailSender emailSender;
    private final EmailMessageGenerator emailMessageGenerator;
    private final ResetPasswordEmailBuilder resetPasswordEmailBuilder;
    private final ConfirmRegistrationEmailBuilder confirmRegistrationEmailBuilder;

    @Inject
    MessageServiceImpl(EmailSender emailSender,
                       EmailMessageGenerator emailMessageGenerator,
                       ResetPasswordEmailBuilder resetPasswordEmailBuilder,
                       ConfirmRegistrationEmailBuilder confirmRegistrationEmailBuilder) {

        this.confirmRegistrationEmailBuilder = confirmRegistrationEmailBuilder;
        this.resetPasswordEmailBuilder = resetPasswordEmailBuilder;
        this.emailMessageGenerator = emailMessageGenerator;
        this.emailSender = emailSender;
    }

    @Override
    public void sendConfirmationEmail(String emailAddress, String tokenId, String redirectionUri) {
        String body = confirmRegistrationEmailBuilder.generateBody(tokenId, redirectionUri);
        String message = emailMessageGenerator.generateBody(CONFIRM_SUBJECT, body);

        sendEmail(emailAddress, CONFIRM_SUBJECT, message);
    }

    @Override
    public void sendPasswordEmail(String emailAddress, String token) {
        String body = resetPasswordEmailBuilder.generateBody(emailAddress, token);
        String message = emailMessageGenerator.generateBody(RESET_PASSWORD_SUBJECT, body);

        sendEmail(emailAddress, RESET_PASSWORD_SUBJECT, message);
    }

    private void sendEmail(String emailAddress, String subject, String message) {
        Email email = EmailBuilder.to(emailAddress)
                .fromAddress(GaeStudioConstants.ARCBEES_MAIL_SENDER)
                .fromPersonal("GAE Studio")
                .body(message)
                .subject(subject)
                .build();

        emailSender.send(email);
    }
}
