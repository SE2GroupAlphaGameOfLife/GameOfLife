package aau.se2.glock.alpha.gameoflife.core.logic;

import java.awt.Color;

import aau.se2.glock.alpha.gameoflife.core.Player;

public class LogicalPlayer {

    int turn;//number of played turns
    int balance;
    int lifePoints;
    Color color;

    LogicalField currentField;

    //TODO implement Family
    //List<Family> familyMembers

    Player player;

    public LogicalPlayer(Player player){
        this.player = player;
        //currentField = startField;
    }

    public int getTurn() {
        return turn;
    }


    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }
}
