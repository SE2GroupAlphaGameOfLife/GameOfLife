package aau.se2.glock.alpha.gameoflife.networking.packages;


import java.net.InetAddress;
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
     * @param players
     */
    public JoinedPlayers(HashMap<Integer, Player> players) {
        this.players = players;
    }

    /**
     * @param player
     * @param ipaddress
     * @return
     */
    public boolean addPlayer(Player player, Integer connectionId) {
        if (this.players.containsKey(connectionId))
            return false;
        this.players.put(connectionId, player);
        return true;
    }

    /**
     * @param ipaddress
     */
    public void removePlayerWithConnectionID(Integer connectionId) {
        this.players.remove(connectionId);
    }

    /**
     * @return
     */
    public HashMap<Integer, Player> getPlayers() {
        return this.players;
    }

    /**
     * @param players
     */
    public void setPlayers(HashMap<Integer, Player> players) {
        this.players = players;
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
        for (Player player : this.players.values()) {
            if (player.getId() == playerId) {
                if (player.isOnline() == false) {
                    setPlayersTurn(playerId + 1);
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
