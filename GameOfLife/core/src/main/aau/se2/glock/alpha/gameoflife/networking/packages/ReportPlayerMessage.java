package aau.se2.glock.alpha.gameoflife.networking.packages;

public class ReportPlayerMessage implements TcpMessage {
    private String payload;

    public ReportPlayerMessage() {

    }

    public ReportPlayerMessage(String payload) {
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }

    @Override
    public void accept(TcpMessageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "ReportPlayerMessage{" +
                "payload='" + payload + '\'' +
                '}';
    }
}
