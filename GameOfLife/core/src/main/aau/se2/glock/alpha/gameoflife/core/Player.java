package aau.se2.glock.alpha.gameoflife.core;

import com.badlogic.gdx.graphics.Color;

import java.security.SecureRandom;

import aau.se2.glock.alpha.gameoflife.core.gamecards.Event;
import aau.se2.glock.alpha.gameoflife.core.jobs.Job;

/**
 *
 */
public class Player {

    /**
     * The position of the player on the game board.
     */
    protected int position;

    /**
     * Amount of steps the player can go.
     */
    protected int moveCount;
    SecureRandom random;
    /**
     * Ingame name and gender of the player.
     */
    private String username, gender;
    /**
     * Job the player currently has.
     */
    private Job currentJob;
    /**
     * Vital values of the player, dependent on the player success.
     */
    private int age, money, id, lifepoints;
    /**
     * Color of the player displayed on the board.
     */
    private Color color;
    /**
     * Tells if the player is the game's host.
     */
    private boolean isHost;
    /**
     * Tells if it's the player's turn.
     */
    private boolean hasTurn;
    /**
     * Tells if the server is only and or the player joining.
     */
    private boolean isJoning, isOnline;

    private boolean hasCheated;
    private int hasCheatedAtAge;

    /**
     * Needed for Kryo Serialization
     */
    public Player() {
    }

    /**
     * Constructor used to initialize the Player object and all it's attributes to a default value.
     *
     * @param username The name of the player. Will be displayed throughout the game.
     * @param isHost   Set true, if the player is the game host, else false.
     */
    public Player(String username, boolean isHost) {
        this.position = 0;
        this.age = 18;
        this.money = 10000;
        this.lifepoints = 0;
        this.color = new Color(Color.rgb888(255, 0, 0));
        this.isHost = isHost;
        this.isJoning = true;
        this.hasTurn = isHost;
        this.username = username;
        this.moveCount = 0;
        this.isOnline = true;
        this.id = 0;
        this.hasCheated = false;
        this.hasCheatedAtAge = 0;
    }

    public int getHasCheatedAtAge() {
        return hasCheatedAtAge;
    }

    public void setHasCheatedAtAge(int hasCheatedAtAge) {
        this.hasCheatedAtAge = hasCheatedAtAge;
    }

    public boolean isHasCheated() {
        return hasCheated;
    }

    public void setHasCheated(boolean hasCheated) {
        this.hasCheated = hasCheated;
    }

    /**
     * @return
     */
    public int getLifepoints() {
        return lifepoints;
    }

    /**
     * @param lifepoints
     */
    public void setLifepoints(int lifepoints) {
        this.lifepoints = lifepoints;
    }

    /**
     * @return
     */
    /*public String getGender() {
        return gender;
    }*/

    /**
     * @param gender
     */
    /*public void setGender(String gender) {
        this.gender = gender;
    }*/

    /**
     * @return
     */
    public int getMoney() {
        return money;
    }

    /**
     * @param money
     */
    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * @return
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return
     */
    public int getMoveCount() {
        return moveCount;
    }

    /**
     * @param moveCount
     */
    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }

    /**
     * @return
     */
    public int getAge() {
        return age;
    }

    /**
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * @return
     */
    public boolean isOnline() {
        return isOnline;
    }

    /**
     * @param online
     */
    public void setOnline(boolean online) {
        isOnline = online;
    }

    /**
     * @return
     */
    public int getId() {
        return this.id;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return
     */
    public boolean isHost() {
        return isHost;
    }

    /**
     * @param host
     */
    public void setHost(boolean host) {
        isHost = host;
    }

    /**
     * @return
     */
    public boolean hasTurn() {
        return hasTurn;
    }

    /**
     * @param hasTurn
     */
    public void setHasTurn(boolean hasTurn) {
        this.hasTurn = hasTurn;
    }

    /**
     * @return
     */
    public boolean isJoning() {
        return isJoning;
    }

    /**
     * @param joning
     */
    public void setJoning(boolean joning) {
        isJoning = joning;
    }

    /**
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return
     */
    public int getPosition() {
        return position;
    }

    /**
     * @param position
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * @return
     */
    public Job getCurrentJob() {
        return currentJob;
    }

    /**
     * @param currentJob
     */
    public void setCurrentJob(Job currentJob) {
        this.currentJob = currentJob;
    }

    /**
     * Generates a random integer between 1 and 10 which represents rolling the dice.
     *
     * @return The generated random integer.
     */
    public int rollTheDice() {
        random = new SecureRandom();
        int randomNumber = random.nextInt(10) + 1; // Generates a random integer between 0 and 9, then adds 1
        this.moveCount = randomNumber;

        return randomNumber;
    }

    /**
     * @param index
     */
    public void chooseDirection(int index) {
        GameField currentField = Board.getInstance().getGameFields().get(this.position);
        this.moveCount--;
        this.position = currentField.getIndexOfNextGameFields().get(index);
    }

    /**
     * @return
     */
    public Event getEvent() {
        Board board = Board.getInstance();
        GameField field = board.getGameFields().get(this.position);
        Event event = field.getLogicalField().getEvent();
        System.out.println("Event triggered:" + event.getText());
        this.money = this.money + event.getCash();
        this.lifepoints = this.lifepoints + event.getLp();
        return event;
    }

    /**
     * @return
     */
    public boolean makeMove() {
        GameField currentField = Board.getInstance().getGameFields().get(this.position);

        while (this.moveCount > 0) {
            if (currentField.getIndexOfNextGameFields().size() > 1) {
                //we have to choose between multiple fields which one we want to choose so we return false
                return false;
            } else {
                this.position = currentField.getIndexOfNextGameFields().get(0);
                this.moveCount--;
                currentField = Board.getInstance().getGameFields().get(this.position);
            }
        }
        //we finished moving return true
        return true;
    }

    @Override
    public String toString() {
        return "Player{" +
                "position=" + position +
                ", moveCount=" + moveCount +
                ", random=" + random +
                ", username='" + username + '\'' +
                ", gender='" + gender + '\'' +
                ", currentJob=" + currentJob +
                ", age=" + age +
                ", money=" + money +
                ", id=" + id +
                ", lifepoints=" + lifepoints +
                ", color=" + color +
                ", isHost=" + isHost +
                ", hasTurn=" + hasTurn +
                ", isJoning=" + isJoning +
                ", isOnline=" + isOnline +
                ", hasCheated=" + hasCheated +
                ", hasCheatedAtAge=" + hasCheatedAtAge +
                '}';
    }
}