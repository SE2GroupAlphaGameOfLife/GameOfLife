package aau.se2.glock.alpha.gameoflife.core.gamecards;


import java.util.ArrayList;
import java.util.List;

public class Card {

    private List<Event> events = new ArrayList<>(4);

    //Index der Liste: 0 = Blau, 1 = Lila, 2 = Grün, 3 = Orange

    public Event getEvent(int n){
       try {
           return events.get(n);
       }catch(Exception e){
           System.out.println(e);
           return null;
       }
    }

    public void fillEvents(){
        for (int i = 0; i < 4; i++) {
            events.add(new Event(0,0,""));
        }
    }
/*
    public void addEvents(List<Event> newEvents){
        for (int i = 0; i < events.size(); i++) {
            events.set(i, newEvents.get(i));
        }
    }

 */

    public void addEvent(int index, Event e){
        events.set(index, e);
    }

}
