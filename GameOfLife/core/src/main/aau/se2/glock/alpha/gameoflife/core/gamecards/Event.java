package aau.se2.glock.alpha.gameoflife.core.gamecards;

public class Event {
    /**
     * Creates event with life points, cash and text.
     *
     * @param lp    life points which are added or subtracted
     * @param cash  cash which is added or subtracted
     * @param text  description of event
     */
    public Event(int lp, int cash, String text) {
        this.lp = lp;
        this.cash = cash;
        this.text = text;
    }

    private int lp;
    private int cash;
    private String text;

    /**
     * Returns life points.
     *
     * @return life points
     */
    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }

    /**
     * Returns cash
     *
     * @return cash
     */
    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    /**
     * Returns text for description.
     *
     * @return text
     */
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
