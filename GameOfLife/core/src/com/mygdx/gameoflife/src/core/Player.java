package com.mygdx.gameoflife.src.core;

import com.badlogic.gdx.graphics.Color;

import java.util.Random;

public class Player {
    private String username, gender;
    private int age, money;
    protected int position;
    private Color color;
    private boolean isHost;
    protected int moveCount;

    public Player(String username, boolean isHost){
        this.position = 0;
        this.age = 18;
        this.money = 10000;
        this.color = new Color(Color.rgb888(255,0,0));
        this.isHost = isHost;
        this.username = username;
        this.moveCount = 0;
    }

    public String getUsername() {
        return username;
    }

    public int getPosition() {
        return position;
    }

    /**
     * Generates a random integer between 1 and 10 which represents rolling the dice.
     *
     * @return The generated random integer.
     */
    public int rollTheDice() {
        Random random = new Random();
        int randomNumber = random.nextInt(10) + 1; // Generates a random integer between 0 and 9, then adds 1
        this.moveCount = randomNumber;

        return randomNumber;
    }

    public void chooseDirection(int index) {
        GameField currentField = Board.getInstance().getGameFields().get(this.position);
        this.moveCount--;
        this.position = currentField.getIndexOfNextGameFields().get(index);
    }

    public boolean makeMove(){
        GameField currentField = Board.getInstance().getGameFields().get(this.position);

        while (this.moveCount > 0){
            this.moveCount--;

            if(currentField.getIndexOfNextGameFields().size() > 1){
                //we have to choose between multiple fields which one we want to choose so we return false
                return false;
            } else {
                this.position = currentField.getIndexOfNextGameFields().get(0);
                currentField = Board.getInstance().getGameFields().get(this.position);
            }
        }

        //we finished moving return true
        return true;
    }

    public void setMoveCount(int moveCount){
        this.moveCount = moveCount;
    }
}
