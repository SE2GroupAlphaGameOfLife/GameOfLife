package aau.se2.glock.alpha.gameoflife.networking.packages;

public interface TcpMessage {
    void accept(TcpMessageVisitor visitor);
}

