package aau.se2.glock.alpha.gameoflife.core;

import com.badlogic.gdx.graphics.Color;

import java.util.Random;

import aau.se2.glock.alpha.gameoflife.core.gamecards.Event;
import aau.se2.glock.alpha.gameoflife.core.jobs.Job;

public class Player {
    private String username, gender;
    private Job currentJob;
    private int age, money, id, lifepoints;

    protected int position;
    private Color color;
    private boolean isHost, hasTurn, isJoning, isOnline;
    protected int moveCount;


    // !!! Needed for Kryo Serialization !!!
    public Player(){}

    public Player(String username, boolean isHost){
        this.position = 0;
        this.age = 18;
        this.money = 10000;
        this.lifePoints = 0;
        this.color = new Color(Color.rgb888(255,0,0));
        this.isHost = isHost;
        this.isJoning = true;
        this.hasTurn = isHost;
        this.username = username;
        this.moveCount = 0;
        this.isOnline = true;
        this.id = 0;

    }

    public int getLifepoints() {
        return lifepoints;
    }

    public void setLifepoints(int lifepoints) {
        this.lifepoints = lifepoints;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setHost(boolean host) {
        isHost = host;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public boolean isHost() {
        return isHost;
    }

    public boolean isHasTurn() {
        return hasTurn;
    }

    public void setHasTurn(boolean hasTurn) {
        this.hasTurn = hasTurn;
    }

    public boolean isJoning() {
        return isJoning;
    }

    public void setJoning(boolean joning) {
        isJoning = joning;
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
    public Event getEvent(){
        Board board = Board.getInstance();
        GameField field = board.getGameFields().get(this.position);
        Event event = field.getLogicalField().getEvent();
        System.out.println("Event triggered:"+event.getText());
        this.money = this.money+ event.getCash();
        this.lifePoints = this.lifePoints+event.getLp();
        return event;



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
