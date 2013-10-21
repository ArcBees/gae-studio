package com.arcbees.gaestudio.client.application.visualizer.widget.entity;

public class IMHandle {
    private String protocol;
    private String address;

    public IMHandle() {
    }

    public IMHandle(String protocol, String address) {
        this.protocol = protocol;
        this.address = address;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
