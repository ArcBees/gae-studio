package com.arcbees.gaestudio.server.service.mail;

public interface MessageService {
    void sendConfirmationEmail(String emailAddress, String tokenId, String redirectionUri);

    void sendPasswordEmail(String emailAddress, String token);
}
