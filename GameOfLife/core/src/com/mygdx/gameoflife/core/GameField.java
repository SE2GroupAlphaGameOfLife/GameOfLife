package com.mygdx.gameoflife.core;

import com.badlogic.gdx.math.Vector2;

import java.util.List;

/**
 * Represents a game field with a position and a list of possible next gameFields.
 */
public class GameField {
    private Vector2 position; // The position of the game field
    private List<Integer> indexOfNextGameFields; // The list of next gameFields for the game field

    /**
     * Constructs a new GameField object with the specified position and next gameFields.
     *
     * @param position              The position of the gameField.
     * @param indexOfNextGameFields The list of next possible gameFields for the gameField.
     */
    public GameField(Vector2 position, List<Integer> indexOfNextGameFields) {
        this.position = position; // Initialize the position field with the provided position parameter
        this.indexOfNextGameFields = indexOfNextGameFields; // Initialize the nextPositions field with the provided nextPositions parameter
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
}
