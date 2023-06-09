package aau.se2.glock.alpha.gameoflife.core;

import com.badlogic.gdx.utils.SerializationException;

import java.util.ArrayList;
import java.util.List;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.logic.LogicalField;
import aau.se2.glock.alpha.gameoflife.core.utilities.IO.JsonCallback;
import aau.se2.glock.alpha.gameoflife.core.utilities.IO.JsonFileReader;

/**
 * Represents a game board with a list of gameFields.
 */
public class Board {

    /**
     *
     */
    private static Board INSTANCE;

    /**
     *
     */
    private List<GameField> gameFields; // The list of gameFields on the board

    /**
     *
     */
    private JsonFileReader jsonFileReader;

    /**
     *
     */
    public Board() {
        this.jsonFileReader = new JsonFileReader();
        this.parseJobsJson();
    }

    /**
     * FOR TESTING ONLY!!
     *
     * @param jsonFileReader
     */
    public Board(JsonFileReader jsonFileReader) {
        this.jsonFileReader = jsonFileReader;
        this.parseJobsJson();
    }

    /**
     * @return
     */
    public static Board getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Board();
        }
        return INSTANCE;
    }

    /**
     * For testing only!
     *
     * @param instance The instance of the Board to set.
     */
    public static void setInstance(Board instance) {
        INSTANCE = instance;
    }

    /**
     * FOR TESTING ONLY!!
     *
     * @param jsonFileReader
     * @return
     */
    public static Board getInstance(JsonFileReader jsonFileReader) {
        if (INSTANCE == null) {
            INSTANCE = new Board(jsonFileReader);
        }
        return INSTANCE;
    }

    /**
     *
     */
    public void parseJobsJson() {
        try {
            this.jsonFileReader.readJson(GameOfLife.FILE_GAMEFIELD_JSON, GameField.class, new JsonCallback<GameField>() {
                @Override
                public void onJsonRead(ArrayList<GameField> result) {
                    gameFields = result;
                    attachLogicalFields();
                    System.out.println(result);
                }
            });
        } catch (SerializationException e) {
            //Gdx.app.log("JobData", e.getMessage());
        }
    }

    /**
     * Returns the list of gameFields on the board.
     *
     * @return The list of gameFields on the board.
     */
    public List<GameField> getGameFields() {
        return gameFields;
    }

    private void attachLogicalFields() {
        for (GameField gameField : this.gameFields) {
            gameField.setLogicalField(new LogicalField(gameField, gameField.getType()));
        }
    }
}