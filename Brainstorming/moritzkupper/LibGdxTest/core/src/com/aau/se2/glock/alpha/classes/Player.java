package com.aau.se2.glock.alpha.classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    private float x, y;
    private float lengthWidth;
    private Texture texture;
    private MapField currentField;

    public Player(float x, float y, float lengthWidth, Texture texture, MapField currentField) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        this.lengthWidth = lengthWidth;
        this.currentField = currentField;
    }
    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, lengthWidth, lengthWidth);
    }

    public void move(SpriteBatch batch, MapField fieldToMove) {
        this.x = fieldToMove.getX();
        this.y = fieldToMove.getY();
        draw(batch);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getLengthWidth() {
        return lengthWidth;
    }

    public void setLengthWidth(float lengthWidth) {
        this.lengthWidth = lengthWidth;
    }

    public MapField getCurrentField() {
        return currentField;
    }

    public void setCurrentField(MapField currentField) {
        this.currentField = currentField;
    }


}
