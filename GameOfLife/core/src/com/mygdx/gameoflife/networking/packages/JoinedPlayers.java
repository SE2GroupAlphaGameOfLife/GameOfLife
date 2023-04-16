package com.mygdx.gameoflife.networking.packages;

import com.mygdx.gameoflife.core.Player;

import java.util.ArrayList;

public class JoinedPlayers {
    private ArrayList<Player> players;

    public JoinedPlayers(){
        players = new ArrayList<Player>();
    }
    public JoinedPlayers(ArrayList<Player> players){
        this.players = players;
    }

    public boolean addPlayer(Player player){
        if(this.players.contains(player))
            return false;
        return this.players.add(player);
    }

    public boolean removePlayer(Player player){
        return this.players.remove(player);
    }

    public void setPlayers(ArrayList<Player> players){
        this.players = players;
    }

    public ArrayList<Player> getPlayers(){
        return this.players;
    }
}
