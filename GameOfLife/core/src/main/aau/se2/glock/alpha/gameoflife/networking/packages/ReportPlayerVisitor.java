package aau.se2.glock.alpha.gameoflife.networking.packages;

import com.badlogic.gdx.Gdx;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.Player;
import aau.se2.glock.alpha.gameoflife.core.logic.PlayerCheated;

public class ReportPlayerVisitor implements TcpMessageVisitor {
    @Override
    public void visit(ReportPlayerMessage message) {
        int playerId = Integer.parseInt(message.getPayload());

        Player player = null;
        for (Player tempPlayer : GameOfLife.players) {
            if (tempPlayer.getId() == playerId) {
                player = tempPlayer;
                break;
            }
        }

        if (player == null) {
            return;
        }

        for (PlayerCheated playerCheated : GameOfLife.server.getPlayerCheatedList()) {
            if (playerCheated.getPlayerId() == playerId && playerCheated.getCheatedAtAge() >= player.getAge() - 5) {
                player.setPosition(player.getPosition() - playerCheated.getAmountCheated());
                player.setMoney(player.getMoney() - (player.getMoney() / 10));
                player.setLifepoints(player.getLifepoints() - (player.getLifepoints() / 10));
                GameOfLife.server.getPlayerCheatedList().remove(playerCheated);
                return;
            }
        }
    }

    @Override
    public void visit(CheatingMessage message) {
    }
}
