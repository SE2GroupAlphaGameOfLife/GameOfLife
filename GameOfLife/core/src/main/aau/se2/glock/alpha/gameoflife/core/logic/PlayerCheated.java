package aau.se2.glock.alpha.gameoflife.core.logic;

public class PlayerCheated {
    private int cheatedAtAge;
    private int playerId;
    private int amountCheated;

    public PlayerCheated(int cheatedAtAge, int playerId, int amountCheated){
        this.amountCheated = amountCheated;
        this.cheatedAtAge = cheatedAtAge;
        this.playerId = playerId;
    }

    public int getCheatedAtAge() {
        return cheatedAtAge;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getAmountCheated() {
        return amountCheated;
    }

    @Override
    public String toString() {
        return "PlayerCheated{" +
                "cheatedAtAge=" + cheatedAtAge +
                ", playerId=" + playerId +
                ", amountCheated=" + amountCheated +
                '}';
    }
}
