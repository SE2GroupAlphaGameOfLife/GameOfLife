package aau.se2.glock.alpha.gameoflife.core.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.SerializationException;

import java.util.ArrayList;
import java.util.List;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.utilities.IO.JsonCallback;
import aau.se2.glock.alpha.gameoflife.core.utilities.IO.JsonFileReader;

public class SpecialEventData {
    private List<SpecialEvent> specialEventList;
    private JsonFileReader jsonFileReader;

    private String eventDataJson;

    public SpecialEventData() {
        this.eventDataJson = GameOfLife.FILE_EVENT_JSON;
        this.jsonFileReader = new JsonFileReader();
        this.specialEventList = new ArrayList<SpecialEvent>();
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
            Gdx.app.log("SpecialEventData", e.getMessage());
        }
    }
}
