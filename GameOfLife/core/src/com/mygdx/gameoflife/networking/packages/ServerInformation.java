package com.mygdx.gameoflife.networking.packages;

import java.net.InetAddress;

public class ServerInformation {
    private int tcpPort;
    private String hostname;

    public ServerInformation(String hostname, int tcpPort){
        this.hostname = hostname;
        this.tcpPort = tcpPort;
    }

    public int getTcpPort() {
        return this.tcpPort;
    }

    public String getHostname() {
        return this.hostname;
    }
}
