package com.aau.se2.glock.alpha.classes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MapField {
    private int number;
    private float lengthWidth;
    private float x, y;
    private Texture texture;

    public MapField(float x, float y, float lengthWidth, Texture texture) {
        this.x = x;
        this.y = y;
        this.lengthWidth = lengthWidth;
        this.texture = texture;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y, lengthWidth, lengthWidth);
    }
}
