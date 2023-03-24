package com.example.gameoflife.game;

public class Player {

    private String username;
    private int age;
    private Color color;
    private int money;
    private boolean isHost;

    public Player(String username, int age, Color color, int money, boolean isHost) {
        this.username = username;
        this.age = age;
        this.color = color;
        this.money = money;
        this.isHost = isHost;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }
}
