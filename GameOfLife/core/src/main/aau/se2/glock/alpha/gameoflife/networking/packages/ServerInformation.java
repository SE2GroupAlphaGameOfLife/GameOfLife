package aau.se2.glock.alpha.gameoflife.networking.packages;

import java.net.InetAddress;
import java.util.Objects;

/**
 *
 */
public class ServerInformation {

    /**
     *
     */
    private int tcpPort;

    /**
     *
     */
    private String hostname;

    /**
     *
     */
    private InetAddress address;

    /**
     * @param hostname
     * @param tcpPort
     */
    public ServerInformation(String hostname, int tcpPort) {
        this.hostname = hostname;
        this.tcpPort = tcpPort;
        this.address = null;
    }

    /**
     *
     */
    public ServerInformation() {
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerInformation that = (ServerInformation) o;
        return Objects.equals(address, that.address);
    }

    /**
     * @return
     */
    public InetAddress getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(InetAddress address) {
        this.address = address;
    }

    /**
     * @return
     */
    public int getTcpPort() {
        return this.tcpPort;
    }

    /**
     * @return
     */
    public String getHostname() {
        return this.hostname;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "ServerInformation{" +
                "hostname='" + hostname + '\'' +
                ", address=" + address +
                '}';
    }
}
