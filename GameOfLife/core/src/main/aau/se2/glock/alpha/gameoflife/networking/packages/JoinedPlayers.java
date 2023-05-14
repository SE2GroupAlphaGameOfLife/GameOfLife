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
    private HashMap<InetAddress, Player> players;

    /**
     *
     */
    public JoinedPlayers() {
        players = new HashMap<InetAddress, Player>();
    }

    /**
     * @param players
     */
    public JoinedPlayers(HashMap<InetAddress, Player> players) {
        this.players = players;
    }

    /**
     * @param player
     * @param ipaddress
     * @return
     */
    public boolean addPlayer(Player player, InetAddress ipaddress) {
        if (this.players.containsKey(ipaddress))
            return false;
        this.players.put(ipaddress, player);
        return true;
    }

    /**
     * @param ipaddress
     */
    public void removePlayerWithIP(InetAddress ipaddress) {
        this.players.remove(ipaddress);
    }

    /**
     * @return
     */
    public HashMap<InetAddress, Player> getPlayers() {
        return this.players;
    }

    /**
     * @param players
     */
    public void setPlayers(HashMap<InetAddress, Player> players) {
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
                player.setHasTurn(true);
            }
        }
    }
}
