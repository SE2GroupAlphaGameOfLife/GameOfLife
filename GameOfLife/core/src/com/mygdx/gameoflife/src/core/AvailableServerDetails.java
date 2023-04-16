package com.mygdx.gameoflife.src.core;

public class AvailableServerDetails {
    private String host;
    private String ip;

    public AvailableServerDetails(String host, String ip) {
        this.host = host;
        this.ip = ip;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
