/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.client.application.support;

public class MessageRequest {
    public static MessageRequest fromSupportMessage(SupportMessage supportMessage) {
        MessageRequest messageRequest = new MessageRequest();

        messageRequest.setBody(supportMessage.getEmail() + " (" + supportMessage.getCompanyName() + ") - " +
                supportMessage.getBody());
        messageRequest.setTo("queenbee@arcbees.com");
        messageRequest.setPersonal("GAE Studio - Support Form");
        messageRequest.setSubject(supportMessage.getSubject());

        return messageRequest;
    }

    private String to;
    private String subject;
    private String body;
    private String personal;

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
