package aau.se2.glock.alpha.gameoflife.core.gamecards;


import java.util.ArrayList;
import java.util.List;

public class Card {

    /**
     *
     */
    private List<Event> events = new ArrayList<>(4);

    /**
     * Returns the event at index n.
     * <p>
     * Index of the list: 0 = blue, 1 = purple, 2 = green, 3 = orange.
     *
     * @param n describes the index
     * @return event at n
     */
    public Event getEvent(int n) {
        try {
            return events.get(n);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Needed for testing
     *
     * @return
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * Fills up events.
     */
    public void fillEvents() {
        for (int i = 0; i < 4; i++) {
            events.add(new Event(0, 0, ""));
        }
    }
/*
    public void addEvents(List<Event> newEvents){
        for (int i = 0; i < events.size(); i++) {
            events.set(i, newEvents.get(i));
        }
    }

 */

    /**
     * Adds event at index
     *
     * @param index index
     * @param e     event
     */
    public void setEvent(int index, Event e) {
        events.set(index, e);
    }

}
