package com.aau.se2.glock.alpha;

import com.aau.se2.glock.alpha.Components.Field;
import com.aau.se2.glock.alpha.Components.Map;
import com.aau.se2.glock.alpha.Components.Model;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.List;

public class Game extends ApplicationAdapter {
    private static final int NUMBER_TOTAL_FIELDS = 10;
    private Stage stage;
    private SpriteBatch batch;
    private Map map;
    private float screenWidth;
    private float screenHeight;
    private float spaceToScreenEnding;

    private static final Game game = new Game();

    public static Game getInstance() {
        return game;
    }

    @Override
    public void create() {
        Stage stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();

        // ToDo: MainMenu with Connection and waiting for Players...

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        spaceToScreenEnding = screenHeight / 20;

        createMap();
        createFields();
        createPlayers();

        startGame();
    }

    private void startGame() {
    }

    private void createPlayers() {
    }

    private void createFields() {
        float fieldSideLength = screenWidth/15;
        List<Field> fields = new ArrayList<>();
        for (int i = 1; i <= NUMBER_TOTAL_FIELDS; i++) {
            Model model = new Model();
            Field f = new Field(i, "Field_"+i, model);
        }
    }

    private void createMap() {
        map = new Map();
        map.setModel(new Model(screenWidth, screenHeight, 0, 0, new Texture("mapTexture.png")));
        map.draw(batch);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public Stage getStage() {
        return stage;
    }
}