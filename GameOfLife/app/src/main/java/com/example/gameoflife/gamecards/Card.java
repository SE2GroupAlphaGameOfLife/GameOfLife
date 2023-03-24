package com.example.gameoflife.gamecards;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Card {

    private List<Event> events = new ArrayList<>(4);

    //Index der Liste: 0 = Blau, 1 = Lila, 2 = Grün, 3 = Orange

    public Event getEvent(int n){
       try {
           return events.get(n);
       }catch(Exception e){
           Log.w("DEBUG",e);
           return null;
       }
    }

    public void addEvents(List<Event> newEvents){
        //TODO EVENT ZU KARTE HINZUFÜGEN
        for (int i = 0; i < events.size(); i++) {
            events.set(i, newEvents.get(i));
        }
    }

}
