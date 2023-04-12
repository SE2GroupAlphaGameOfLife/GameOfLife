package com.aau.se2.glock.alpha;

import com.aau.se2.glock.alpha.classes.MapField;
import com.aau.se2.glock.alpha.classes.Player;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.List;

public class GameOfLife extends ApplicationAdapter {
    private Stage stage;
    private Player currentPlayer;
    private SpriteBatch batch;
    private Texture playerTexture;
    private Texture mapTexture;
    private Texture fieldTexture;
    private Texture diceTexture;
    ImageButton btn_rollDice;
    private List<MapField> mapFields;
    private List<Player> players;
    private float screenWidth;
    private float screenHeight;
    float spaceToScreenEnding;

    @Override
    public void create() {
        Stage stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        spaceToScreenEnding = screenHeight / 20;

        batch = new SpriteBatch();
        playerTexture = new Texture("Player01.png");
        mapTexture = new Texture("Map01.png");
        fieldTexture = new Texture("Field01.png");
        diceTexture = new Texture("Dice.png");

        Drawable drawable = new TextureRegionDrawable(new TextureRegion(diceTexture));
        btn_rollDice = new ImageButton(drawable);
        btn_rollDice.setX(spaceToScreenEnding);
        btn_rollDice.setY(screenHeight - btn_rollDice.getHeight() - spaceToScreenEnding);
        btn_rollDice.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                batch.begin();
                rollDice();
                batch.end();
                return true;
            }
        });
        stage.addActor(btn_rollDice);

        float fieldLengthWidth = screenWidth / 15;
        float playerLengthWidth = screenWidth / 45;
        float x;
        float y;


        mapFields = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            x = spaceToScreenEnding + i * (fieldLengthWidth);
            y = spaceToScreenEnding;
            mapFields.add(new MapField(x, y, fieldLengthWidth, fieldTexture));
        }

        players = new ArrayList<>();
        MapField firstField = mapFields.get(0);
        for (int i = 0; i < 1; i++) {
            players.add(new Player(firstField.getX() + firstField.getLengthWidth() / 3, firstField.getY() + firstField.getLengthWidth() / 3, playerLengthWidth, playerTexture, mapFields.get(0)));
        }
        currentPlayer = players.get(0);

        batch.begin();
        batch.draw(mapTexture, 0, 0, screenWidth, screenHeight);
        for (MapField f : mapFields) {
            f.draw(batch);
        }
        for (Player p : players) {
            p.draw(batch);
        }
        btn_rollDice.draw(batch, 1f);
        batch.end();
    }

    private void rollDice() {
        //random number 1-6
        int num = 5;
        currentPlayer.move(batch, mapFields.get(currentPlayer.getCurrentField().getNumber() + num));
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
