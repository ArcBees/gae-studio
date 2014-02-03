/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.license;

import java.io.Serializable;

import com.google.inject.servlet.SessionScoped;

@SessionScoped
public class LicenseSession implements Serializable {
    private Boolean licenseValid;

    public Boolean getLicenseValid() {
        return licenseValid;
    }

    public void setLicenseValid(Boolean licenseValid) {
        this.licenseValid = licenseValid;
    }
}
