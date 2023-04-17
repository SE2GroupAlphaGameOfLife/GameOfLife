package com.aau.se2.glock.alpha.Components;

public class PlayerSlot {
    private float x;
    private float y;
    private boolean isFree;

    public PlayerSlot(float x, float y, boolean isFree) {
        this.x = x;
        this.y = y;
        this.isFree = isFree;
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

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }
}
