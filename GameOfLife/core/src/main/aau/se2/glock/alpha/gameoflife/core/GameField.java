package aau.se2.glock.alpha.gameoflife.core;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import aau.se2.glock.alpha.gameoflife.core.logic.LogicalField;

/**
 * Represents a game field with a position and a list of possible next gameFields.
 */
public class GameField {

    /**
     *
     */
    private final Vector2 position; // The position of the game field

    /**
     *
     */
    private final List<Integer> indexOfNextGameFields; // The list of next gameFields for the game field

    /**
     *
     */
    private LogicalField logicalField;  //Logical field which has Information on events

    private final String type;

    /**
     * For serialization needed!
     */
    public GameField() {
        this.logicalField = null;
        this.position = null;
        this.indexOfNextGameFields = new ArrayList<>();
        this.type = null;
    }

    /**
     * FOR TESTING ONLY!
     *
     * @param logicalField
     */
    public GameField(LogicalField logicalField) {
        this.logicalField = logicalField;
        this.position = null;
        this.indexOfNextGameFields = null;
        this.type = null;
    }

    /**
     * Constructs a new GameField object with the specified position and next gameFields.
     *
     * @param position              The position of the gameField.
     * @param indexOfNextGameFields The list of next possible gameFields for the gameField.
     */
    public GameField(Vector2 position, List<Integer> indexOfNextGameFields, String type) {
        this.type = type;
        this.position = position; // Initialize the position field with the provided position parameter
        this.indexOfNextGameFields = indexOfNextGameFields; // Initialize the nextPositions field with the provided nextPositions parameter
        this.logicalField = new LogicalField(this,type);
    }

    public String getType() {
        return type;
    }

    /**
     * Returns the position of the gameField.
     *
     * @return The position of the gameField.
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Returns a list of next possible gameFields for the current gameField.
     *
     * @return list of next gameField for the gameField.
     */
    public List<Integer> getIndexOfNextGameFields() {
        return indexOfNextGameFields;
    }

    /**
     * @return
     */
    public LogicalField getLogicalField() {
        return this.logicalField;
    }

    public void setLogicalField(LogicalField logicalField) {
        this.logicalField = logicalField;
    }

    /**
     * FOR TESTING ONLY
     *
     * @param index
     */
    public void addIndexOfNextGameField(int index) {
        indexOfNextGameFields.add(index);
    }
}