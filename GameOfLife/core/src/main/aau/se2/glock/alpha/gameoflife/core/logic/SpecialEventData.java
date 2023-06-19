package aau.se2.glock.alpha.gameoflife.core.logic;

import com.badlogic.gdx.utils.SerializationException;

import java.util.ArrayList;
import java.util.List;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.utilities.IO.JsonCallback;
import aau.se2.glock.alpha.gameoflife.core.utilities.IO.JsonFileReader;

public class SpecialEventData {

    private static SpecialEventData INSTANCE;
    private List<SpecialEvent> specialEventList;
    private JsonFileReader jsonFileReader;

    private String eventDataJson;

    /**
     * Needed For Serialisation
     */
    public SpecialEventData() {
        this.eventDataJson = GameOfLife.FILE_SPECIAL_EVENT_JSON;
        this.jsonFileReader = new JsonFileReader();
        this.specialEventList = new ArrayList<SpecialEvent>();
        fillSpecialEventList();
    }

    /**
     * For Testing
     *
     * @param jsonFileReader
     */
    public SpecialEventData(JsonFileReader jsonFileReader) {
        this.eventDataJson = GameOfLife.FILE_SPECIAL_EVENT_JSON;
        this.jsonFileReader = jsonFileReader;
        this.specialEventList = new ArrayList<SpecialEvent>();
    }

    public static SpecialEventData getINSTANCE() {
        if (INSTANCE != null) {
            return INSTANCE;
        } else return INSTANCE = new SpecialEventData();


    }

    public List<SpecialEvent> getSpecialEventList() {
        return specialEventList;
    }

    public void parseSpecialEventsJson() {
        try {
            this.jsonFileReader.readJson(this.eventDataJson, SpecialEvent.class, new JsonCallback<SpecialEvent>() {
                @Override
                public void onJsonRead(ArrayList<SpecialEvent> result) {
                    specialEventList = result;
                }
            });
        } catch (SerializationException e) {
            // Gdx.app.log("SpecialEventData", e.getMessage());
        }
    }

    public void fillSpecialEventList() {
        this.parseSpecialEventsJson();
    }
}

