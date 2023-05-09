package aau.se2.glock.alpha.gameoflife.core.logic;

import java.util.List;

import aau.se2.glock.alpha.gameoflife.core.Board;
import aau.se2.glock.alpha.gameoflife.core.Player;

//Should later be refactored to be a singleton
public class Game {

    public static Game INSTANCE;
    private Board board;
    private Player currentplayer;
    private final List<Player> otherPlayers;

    private Game(Player player, List<Player> otherPlayers) {
        this.currentplayer = player;
        this.otherPlayers = otherPlayers;
    }

    public static Game getInstance(Player player, List<Player> otherPlayers) {
        if (INSTANCE == null) {
            INSTANCE = new Game(player, otherPlayers);
        }
        return INSTANCE;
    }

    public static Game getInstance() {
        return INSTANCE;
    }

    /**
     * Called when host presses the start button/the host is initialized
     * sets up all logic components
     */
    public void startGame() {
        board = Board.getInstance();

        //sendInfoToOtherPlayers();


    }

    public void getFieldEvent() {
        if (!currentplayer.isHasTurn()) {
        } else {
            currentplayer.getEvent();

        }

    }

    /**
     * called when the current player presses end turn
     */
    public void endTurn() {
        currentplayer.setHasTurn(false);
        if (currentplayer.getId() >= otherPlayers.size() - 1) {
            currentplayer = otherPlayers.get(0);
        } else {
            currentplayer = otherPlayers.get(currentplayer.getId() + 1);
        }


    }

    public Player getCurrentPlayer() {
        return currentplayer;
    }

    public List<Player> getOtherPlayers() {
        return otherPlayers;
    }
}
