package aau.se2.glock.alpha.gameoflife.networking.packages;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.Player;
import aau.se2.glock.alpha.gameoflife.core.logic.PlayerCheated;

public class ReportPlayerVisitor implements TcpMessageVisitor {
    @Override
    public void visit(ReportPlayerMessage message) {
        System.out.println("Processing ReportPlayerMessage with payload: " + message.getPayload());
        int playerId = Integer.parseInt(message.getPayload());

        Player player = null;
        for (Player tempPlayer : GameOfLife.players) {
            if(tempPlayer.getId() == playerId){
                player = tempPlayer;
                break;
            }
        }

        if(player == null){
            return;
        }

        for(PlayerCheated playerCheated : GameOfLife.server.getPlayerCheatedList()){
            if(playerCheated.getPlayerId() == playerId && playerCheated.getCheatedAtAge() >= player.getAge() - 5){
                player.setPosition(player.getPosition()-playerCheated.getAmountCheated());
                return;
            }
        }
        System.out.println("Player has not cheated in the last 5 rounds");
    }

    @Override
    public void visit(CheatingMessage message) {
    }
}