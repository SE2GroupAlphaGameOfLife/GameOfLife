package com.example.gameoflife.game;

import java.util.ArrayList;

public class Game {

    private ArrayList<Player> players;
    private Player host;

    public Game(ArrayList<Player> players, Player host) {
        this.players = players;
        this.host = host;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public Game(ArrayList<Player> players) {
        this.players = players;
    }

}
