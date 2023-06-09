package aau.se2.glock.alpha.gameoflife.networking.packages;

public class CheatingMessage implements TcpMessage {
    private String payload;
    private String command;

    public CheatingMessage() {

    }

    public CheatingMessage(String payload) {
        this.payload = payload;
        this.command = "CHEAT";
    }

    public String getPayload() {
        return payload;
    }

    public String getCommand() {
        return command;
    }

    @Override
    public void accept(TcpMessageVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "CheatingMessage{" +
                "payload='" + payload + '\'' +
                ", command='" + command + '\'' +
                '}';
    }
}
