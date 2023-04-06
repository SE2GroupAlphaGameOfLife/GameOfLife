package com.aau.se2.glock.alpha.Components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;

public class Field {
    private int number;
    private String name;
    private Model model;
    private PlayerSlot[] playerSlots;

    public Field(int number, String name, Model model) {
        this.number = number;
        this.name = name;
        this.model = model;
        this.playerSlots = new PlayerSlot[3];
        fillPlayerSlots();
    }

    private void fillPlayerSlots() {
        for (int i = 0; i < 3; i++) {
            playerSlots[i] = new PlayerSlot(0f, 0f, true);
        }
        calculatePlayerSlotPositions();
    }

    private void calculatePlayerSlotPositions() {
        for (int i = 0; i < 3; i++) {
            playerSlots[i].setX(model.getX() + i * ((model.getWidth()/3)));
            playerSlots[i].setY(model.getY());
        }
    }

    public PlayerSlot[] getPlayerSlots() {
        return playerSlots;
    }

    public void setPlayerSlots(PlayerSlot[] playerSlots) {
        this.playerSlots = playerSlots;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
