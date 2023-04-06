package com.aau.se2.glock.alpha.Components;

import com.aau.se2.glock.alpha.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.Color;
import java.util.List;

public class Player {
    private static int uidCounter = 1;
    private int uid;
    private String username;
    private Model model;
    private Field currentField;

    public Player(String username, Model model, Field currentField) {
        this.uid = uidCounter++;
        this.username = username;
        this.model = model;
        this.currentField = currentField;
        setPlayerSlot();
    }

    private void setPlayerSlot() {
        PlayerSlot slot = getNextAvailablePlayerSlot();
        if(slot!=null){
            model.setX(slot.getX());
            model.setY(slot.getY());
            slot.setFree(false);
        }else {
            // ToDo: throw Exception
        }
    }

    private PlayerSlot getNextAvailablePlayerSlot() {
        for(PlayerSlot ps : currentField.getPlayerSlots()){
            if(ps.isFree()){
                return ps;
            }
        }
        return null;
    }

    public Field getCurrentField() {
        return currentField;
    }

    public void setCurrentField(Field currentField) {
        this.currentField = currentField;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void moveToField(Field fieldToMove, SpriteBatch batch) {
        // ToDo: check if fields in between are fields where you must stop!
        this.model.setX(fieldToMove.getModel().getX());
        this.model.setY(fieldToMove.getModel().getY());
        currentField = fieldToMove;
        setPlayerSlot();
        model.draw(batch);
    }
}
