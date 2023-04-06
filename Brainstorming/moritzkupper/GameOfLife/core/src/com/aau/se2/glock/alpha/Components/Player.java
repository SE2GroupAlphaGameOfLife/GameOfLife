package com.aau.se2.glock.alpha.Components;

import java.awt.Color;
import java.util.List;

public class Player {
    private int uid;
    private String username;
    private Color color;
    private Model model;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
