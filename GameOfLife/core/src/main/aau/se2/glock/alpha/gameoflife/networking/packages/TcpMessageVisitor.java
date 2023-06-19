package aau.se2.glock.alpha.gameoflife.networking.packages;

public interface TcpMessageVisitor {
    void visit(ReportPlayerMessage message);

    void visit(CheatingMessage message);
}
