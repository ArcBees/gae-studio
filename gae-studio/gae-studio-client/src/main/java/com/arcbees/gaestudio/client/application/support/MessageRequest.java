/**
 * Copyright 2015 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.gaestudio.client.application.support;

public class MessageRequest {
    private String to;
    private String subject;
    private String body;
    private String personal;

    public static MessageRequest fromSupportMessage(SupportMessage supportMessage) {
        MessageRequest messageRequest = new MessageRequest();

        messageRequest.setBody(supportMessage.getEmail() + " (" + supportMessage.getCompanyName() + ") - " +
                supportMessage.getBody());
        messageRequest.setTo("queenbee@arcbees.com");
        messageRequest.setPersonal("GAE Studio - Support Form");
        messageRequest.setSubject(supportMessage.getSubject());

        return messageRequest;
    }

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
