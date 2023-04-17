package com.mygdx.gameoflife.networking.packages;

import java.net.InetAddress;
import java.util.Objects;

public class ServerInformation {
    private int tcpPort;
    private String hostname;
    private InetAddress address;

    public ServerInformation(String hostname, int tcpPort) {
        this.hostname = hostname;
        this.tcpPort = tcpPort;
        this.address = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerInformation that = (ServerInformation) o;
        return Objects.equals(address, that.address);
    }

    public ServerInformation() {
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public int getTcpPort() {
        return this.tcpPort;
    }

    public String getHostname() {
        return this.hostname;
    }

    @Override
    public String toString() {
        return "ServerInformation{" +
                "hostname='" + hostname + '\'' +
                ", address=" + address +
                '}';
    }
}
