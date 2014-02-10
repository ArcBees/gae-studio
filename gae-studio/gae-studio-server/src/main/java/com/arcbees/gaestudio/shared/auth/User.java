/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.shared.auth;

import com.arcbees.gaestudio.shared.util.Validation;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

public class User {
    private Long id;
    @NotEmpty(message = Validation.REQUIRED)
    private String email;
    @NotEmpty(message = Validation.REQUIRED)
    private String password;
    private Profile profile = new Profile();
    private Long dateActivated;
    private Date dateCreated;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getDateActivated() {
        return dateActivated;
    }

    public void setDateActivated(Long dateActivated) {
        this.dateActivated = dateActivated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
