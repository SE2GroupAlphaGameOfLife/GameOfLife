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
    private HashMap<String, Player> players;

    /**
     *
     */
    public JoinedPlayers() {
        players = new HashMap<String, Player>();
    }

    /**
     * @param players
     */
    public JoinedPlayers(HashMap<String, Player> players) {
        this.players = players;
    }

    /**
     * @param player
     * @param ipaddress
     * @return
     */
    public boolean addPlayer(Player player, InetAddress ipaddress) {
        if (this.players.containsKey(ipaddress.toString()))
            return false;
        this.players.put(ipaddress.toString(), player);
        return true;
    }

    /**
     * @param ipaddress
     */
    public void removePlayerWithIP(InetAddress ipaddress) {
        this.players.remove(ipaddress.toString());
    }

    /**
     * @return
     */
    public HashMap<String, Player> getPlayers() {
        return this.players;
    }

    /**
     * @param players
     */
    public void setPlayers(HashMap<String, Player> players) {
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
