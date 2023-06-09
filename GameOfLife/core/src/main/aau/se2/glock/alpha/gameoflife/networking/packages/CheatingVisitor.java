package aau.se2.glock.alpha.gameoflife.networking.packages;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.logic.PlayerCheated;

public class CheatingVisitor implements TcpMessageVisitor {
    @Override
    public void visit(ReportPlayerMessage message) {
    }

    @Override
    public void visit(CheatingMessage message) {
        String[] payload = message.getPayload().split("#");
        int cheatedPlayerId = Integer.parseInt(payload[0]);
        int cheatedPlayerAge = Integer.parseInt(payload[1]);
        int cheatedAmount = Integer.parseInt(payload[2]);

        PlayerCheated playerCheated = new PlayerCheated(cheatedPlayerId, cheatedPlayerAge, cheatedAmount);
        GameOfLife.server.getPlayerCheatedList().add(playerCheated);
    }
}
