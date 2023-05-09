package aau.se2.glock.alpha.gameoflife.networking.packages;


import java.net.InetAddress;
import java.util.HashMap;

import aau.se2.glock.alpha.gameoflife.core.Player;

public class JoinedPlayers {
    private HashMap<InetAddress, Player> players;

    public JoinedPlayers() {
        players = new HashMap<InetAddress, Player>();
    }

    public JoinedPlayers(HashMap<InetAddress, Player> players) {
        this.players = players;
    }

    public boolean addPlayer(Player player, InetAddress ipaddress) {
        if (this.players.containsKey(ipaddress))
            return false;
        this.players.put(ipaddress, player);
        return true;
    }

    public void removePlayerWithIP(InetAddress ipaddress) {
        this.players.remove(ipaddress);
    }

    public HashMap<InetAddress, Player> getPlayers() {
        return this.players;
    }

    public void setPlayers(HashMap<InetAddress, Player> players) {
        this.players = players;
    }

    public int getPlayerCount() {
        return this.players.size();
    }

    public void setPlayersTurn(int playerId) {
        playerId = playerId > this.players.size() ? 1 : playerId;
        for (Player player : this.players.values()) {
            if (player.getId() == playerId) {
                player.setHasTurn(true);
            }
        }
    }
}
