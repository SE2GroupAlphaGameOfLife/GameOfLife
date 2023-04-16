package com.mygdx.gameoflife.networking.packages;

public class PingResponse {

    //private long time = System.currentTimeMillis();
    private int tcpPort = 0;

    public int getTcpPort() {
        return tcpPort;
    }

    public void setTcpPort(int tcpPort) {
        this.tcpPort = tcpPort;
    }
}