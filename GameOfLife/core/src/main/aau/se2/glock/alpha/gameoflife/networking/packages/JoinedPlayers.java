package aau.se2.glock.alpha.gameoflife.networking.packages;


import java.util.HashMap;

import aau.se2.glock.alpha.gameoflife.core.Player;

/**
 *
 */
public class JoinedPlayers {

    /**
     *
     */
    private HashMap<Integer, Player> players;

    /**
     *
     */
    public JoinedPlayers() {
        players = new HashMap<Integer, Player>();
    }

    /**
     * @param player
     * @param connectionId
     * @return
     */
    public boolean addPlayer(Player player, Integer connectionId) {
        if (this.players.containsKey(connectionId))
            return false;

        return this.players.put(connectionId, player) == null;
    }

    /**
     * @param connectionId
     */
    public boolean removePlayerWithConnectionID(Integer connectionId) {
        return this.players.remove(connectionId) != null;
    }

    /**
     * @return
     */
    public HashMap<Integer, Player> getPlayers() {
        return this.players;
    }

    /**
     * @return
     */
    public int getPlayerCount() {
        return this.players.size();
    }

    /**
     * @param playerId
     */
    public void setPlayersTurn(int playerId) {
        playerId = playerId > this.players.size() ? 1 : playerId;
        if (this.players.size() <= 1) {
            return;
        }
        for (Player player : this.players.values()) {
            player.setHasTurn(false);
            if (player.getId() == playerId) {
                if (player.isOnline() == false) {
                    setPlayersTurn(playerId + 1);
                    break;
                }
                player.setHasTurn(true);
            }
        }
    }

    @Override
    public String toString() {
        return "JoinedPlayers{" +
                "players=" + players +
                '}';
    }
}
