package com.mygdx.gameoflife.networking.packages;

import java.net.InetAddress;

public class ServerInformation {
    private int tcpPort, udpPort;
    private InetAddress address;

    public ServerInformation(int tcpPort){
        this.tcpPort = tcpPort;
    }

    public int getTcpPort() {
        return tcpPort;
    }
}
