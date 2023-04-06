package com.aau.se2.glock.alpha.Components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;

public class Map {
    private List<Field> fields;
    private Model model;

    public void draw(SpriteBatch batch){
        batch.begin();
        batch.draw(model.getTexture(), model.getX(), model.getY(), model.getWidth(), model.getHeight());
        batch.end();
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
