package aau.se2.glock.alpha.gameoflife.core;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;
import java.util.List;

import aau.se2.glock.alpha.gameoflife.core.utilities.JsonLoader;

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
    private final List<GameField> gameFields; // The list of gameFields on the board

    /**
     *
     */
    public Board(String jsonString) {
        // Parsing the json so we can use it
        JsonReader jsonReader = new JsonReader();
        JsonValue jsonValue = jsonReader.parse(jsonString);

        List<GameField> gameFields = new ArrayList<>();

        // Read the values and create a list of gameFields
        for (JsonValue jsonField : jsonValue) {
            List<Integer> nextPositions = new ArrayList<>();
            for (int i : jsonField.get("nextPositions").asIntArray()) {
                nextPositions.add(i);
            }

            GameField gameField = new GameField(
                    new Vector2(jsonField.get("position").getInt("x"), jsonField.get("position").getInt("y")),
                    nextPositions
            );
            gameFields.add(gameField);
        }
        this.gameFields = gameFields;
    }

    /**
     * @return
     */
    public static Board getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Board(new JsonLoader().loadJsonFile("gameboard.json"));
        }
        return INSTANCE;
    }

    public static Board getInstance(String jsonString) {
        if (INSTANCE == null) {
            INSTANCE = new Board(jsonString);
        }
        return INSTANCE;
    }

    /**
     * Returns the list of gameFields on the board.
     *
     * @return The list of gameFields on the board.
     */
    public List<GameField> getGameFields() {
        return gameFields;
    }
}
