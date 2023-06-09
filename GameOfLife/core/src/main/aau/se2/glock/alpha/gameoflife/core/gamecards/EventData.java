package aau.se2.glock.alpha.gameoflife.core.gamecards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.SerializationException;

import java.util.ArrayList;
import java.util.List;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.utilities.IO.JsonCallback;
import aau.se2.glock.alpha.gameoflife.core.utilities.IO.JsonFileReader;

/**
 *
 */
public class EventData {

    /**
     *
     */
    private JsonFileReader jsonFileReader;

    /**
     *
     */
    private List<Event> eventList;

    /**
     *
     */
    private List<Card> cardList;

    public EventData() {
        this.jsonFileReader = new JsonFileReader();
        this.eventList = new ArrayList<Event>();
        this.cardList = new ArrayList<Card>();
    }

    /**
     * For Testing only
     * @param jsonString
     */
    public EventData(String jsonString) {
        // Parsing the json so we can use it
        JsonReader jsonReader = new JsonReader();
        JsonValue jsonValue = jsonReader.parse(jsonString);

        ArrayList<Event> events = new ArrayList<>();

        // Read the values and create a list of gameFields
        for (JsonValue jsonField : jsonValue) {
            int lp = jsonField.getInt("lp");
            int cash = jsonField.getInt("cash");
            String text = jsonField.getString("text");

            Event event = new Event(lp, cash, text);
            events.add(event);
        }

        this.cardList = new ArrayList<Card>();
        this.eventList = events;
    }

    /**
     *
     */
    public void parseEventsJson() {
        try {
            this.jsonFileReader.readJson(GameOfLife.FILE_EVENT_JSON, Event.class, new JsonCallback<Event>() {
                @Override
                public void onJsonRead(ArrayList<Event> result) {
                    eventList = result;
                }
            });
        } catch (SerializationException e) {
            Gdx.app.log("EventData", e.getMessage());
        }
    }

    /**
     * Adds events to event list.
     */
    public void fillEventList() {
        this.parseEventsJson();
        Gdx.app.log("EventData", "Read from JSON: " + this.eventList);
    }

    /**
     * @return
     */
    public List<Event> getEventList() {
        return eventList;
    }

    /**
     * not relevant for others.
     * fills up card list for further implementations.
     */
    private void fillPseudoCard() {
        for (int i = 0; i < eventList.size() / 4; i++) {
            cardList.add(new Card());
            cardList.get(i).fillEvents();
        }
    }

    /**
     * Calls fillPseudoCard to fill up Array.
     */
    public void fillCardList() {
        fillPseudoCard();

        int index = 0;
        for (int i = 0; i < cardList.size(); i++) {
            for (int j = 0; j < 4; j++) {
                cardList.get(i).setEvent(j, eventList.get(index + j));
            }
            index = index + 4;
        }


    }

    /**
     * @return
     */
    public List<Card> getCardList() {
        return cardList;
    }
}