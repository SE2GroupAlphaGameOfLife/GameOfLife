package aau.se2.glock.alpha.gameoflife.core.logic;

import java.util.ArrayList;
import java.util.List;

public class SpecialEventHandler {

    private String type;

    private SpecialEventData specialEventData;

    public SpecialEventHandler(String type){
        this.type = type;
        specialEventData = SpecialEventData.getINSTANCE();
    }

    public SpecialEvent getCorrectSpecialEvent() {
        List<SpecialEvent> specialEventList = new ArrayList();
        specialEventList = specialEventData.getSpecialEventList();
        for (SpecialEvent specialEvent : specialEventList) {
            if (specialEvent.getType().equals(type)) {
                return specialEvent;
            }
        }
        return null;
    }
}
