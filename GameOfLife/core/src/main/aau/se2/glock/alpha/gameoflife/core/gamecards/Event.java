package aau.se2.glock.alpha.gameoflife.core.gamecards;

public class Event {
    public Event(int lp, int cash, String text) {
        this.lp = lp;
        this.cash = cash;
        this.text = text;
    }

    private int lp;
    private int cash;
    private String text;

    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
