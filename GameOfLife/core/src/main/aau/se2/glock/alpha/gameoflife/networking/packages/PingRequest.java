package main.aau.se2.glock.alpha.gameoflife.networking.packages;

public class PingRequest {
    private boolean updRequest = false;
    private boolean tcpPortRequest = false;

    public PingRequest() {
    }

    public boolean isUpdRequest() {
        return updRequest;
    }

    public void setUpdRequest(boolean updRequest) {
        this.updRequest = updRequest;
    }

    public boolean isTcpPortRequest() {
        return tcpPortRequest;
    }

    public void setTcpPortRequest(boolean tcpPortRequest) {
        this.tcpPortRequest = tcpPortRequest;
    }
}
