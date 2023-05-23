package aau.se2.glock.alpha.gameoflife.core.gamecards;

public class Event {

    /**
     *
     */
    private int lp;

    /**
     *
     */
    private int cash;

    /**
     *
     */
    private String text;

    /**
     * Needed for JSON deserialization
     */
    public Event() {

    }

    /**
     * Creates event with life points, cash and text.
     *
     * @param lp   life points which are added or subtracted
     * @param cash cash which is added or subtracted
     * @param text description of event
     */
    public Event(int lp, int cash, String text) {
        this.lp = lp;
        this.cash = cash;
        this.text = text;
    }

    /**
     * Returns life points.
     *
     * @return life points
     */
    public int getLp() {
        return lp;
    }

    /**
     * @param lp
     */
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

    /**
     * @param cash
     */
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

    /**
     * @param text
     */
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Event{" +
                "lp=" + lp +
                ", cash=" + cash +
                ", text='" + text + '\'' +
                '}';
    }
}