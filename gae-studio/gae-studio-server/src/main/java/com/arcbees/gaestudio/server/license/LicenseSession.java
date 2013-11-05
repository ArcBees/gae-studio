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
