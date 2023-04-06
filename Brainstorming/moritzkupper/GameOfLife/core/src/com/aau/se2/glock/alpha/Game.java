package com.aau.se2.glock.alpha;

import com.aau.se2.glock.alpha.Components.Field;
import com.aau.se2.glock.alpha.Components.Map;
import com.aau.se2.glock.alpha.Components.Model;
import com.aau.se2.glock.alpha.Components.Player;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.List;

public class Game extends ApplicationAdapter {
    private static final int NUMBER_TOTAL_FIELDS = 10;
    private static final String STRING_FIELD_TEXTURE = "fieldTexture.png";
    private static final String STRING_MAP_TEXTURE = "mapTexture.png";
    private static final String STRING_PLAYER_HARRYPOTTER_TEXTURE = "harryPotter.png";
    private static final String STRING_PLAYER_JOHNWICK_TEXTURE = "johnWick.png";
    private static final String STRING_PLAYER_WALTERWHITE_TEXTURE = "walterWhite.png";
    private static final String STRING_DICE_TEXTURE = "diceTexture.png";
    private static final String STRING_FIELD_NAME_PREFIX = "Field_";
    private static final String STRING_PLAYER_NAME_PREFIX = "Player_";

    private Stage stage;
    private SpriteBatch batch;
    private Map map;
    private float screenWidth;
    private float screenHeight;
    private float spaceToScreenEnding;
    private float fontSize;
    List<Player> players;
    ImageButton btn_rollDice;
    Player currentPlayer;
    Label lb_currentPlayer;
    private int roundNumber;


    @Override
    public void create() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        batch = new SpriteBatch();

        // ToDo: MainMenu with Connection and waiting for Players...

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        spaceToScreenEnding = screenHeight / 20;
        fontSize = screenWidth / 300;

        createMap();
        createFields();
        createPlayers();

        startGame();
    }

    private void startGame() {
        currentPlayer = players.get(0);
        createDice();
        createCurrentPlayerLabel();
    }

    private void createCurrentPlayerLabel() {
        lb_currentPlayer = new Label(currentPlayer.getUsername(), new Label.LabelStyle(new BitmapFont(), Color.RED));
        lb_currentPlayer.setFontScale(fontSize);
        lb_currentPlayer.setX(screenWidth - screenWidth / 5 - spaceToScreenEnding);
        lb_currentPlayer.setY(screenHeight - lb_currentPlayer.getHeight() - spaceToScreenEnding);

        stage.addActor(lb_currentPlayer);
        stage.draw();
    }

    private void updateCurrentPlayerLabel() {
        lb_currentPlayer.setText(currentPlayer.getUsername());
        stage.draw();
    }

    private void createDice() {
        Drawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(STRING_DICE_TEXTURE)));
        btn_rollDice = new ImageButton(drawable);
        btn_rollDice.setX(spaceToScreenEnding);
        btn_rollDice.setY(screenHeight - btn_rollDice.getHeight() - spaceToScreenEnding);
        btn_rollDice.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rollDice();
                return true;
            }
        });
        stage.addActor(btn_rollDice);
    }

    private void rollDice() {
        // ToDo: if(player who clicked == currentPlayer)
        // ToDo: roll random number
        int num = 1;
        currentPlayer.moveToField(map.getFields().get(currentPlayer.getCurrentField().getNumber() + num), batch);

        // ToDo: Do Action if required

        // if player is finished
        currentPlayer = getNextPlayer();
    }

    private Player getNextPlayer() {
        try{
            return players.get(players.indexOf(currentPlayer)+1);
        } catch (IndexOutOfBoundsException e){
            roundNumber++;
            return players.get(0);
        }
    }

    private void createPlayers() {
        float playerSideLength = screenWidth / 45;
        List<Model> models = new ArrayList<>();
        //ToDo: for(connectedPlayerCount ...)
        models.add(new Model(playerSideLength, playerSideLength, 1, 1, new Texture(STRING_PLAYER_HARRYPOTTER_TEXTURE)));
        models.add(new Model(playerSideLength, playerSideLength, 1, 1, new Texture(STRING_PLAYER_WALTERWHITE_TEXTURE)));
        models.add(new Model(playerSideLength, playerSideLength, 1, 1, new Texture(STRING_PLAYER_JOHNWICK_TEXTURE)));

        players = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            players.add(new Player(STRING_PLAYER_NAME_PREFIX + (i + 1), models.get(i), map.getFields().get(0)));
        }

        for (Player p : players) {
            p.getModel().draw(batch);
        }
    }

    private void createFields() {
        float fieldSideLength = screenWidth / 15;
        List<Field> fields = new ArrayList<>();
        for (int i = 1; i <= NUMBER_TOTAL_FIELDS; i++) {
            Model model = new Model(fieldSideLength, fieldSideLength, spaceToScreenEnding + (i - 1) * fieldSideLength, spaceToScreenEnding, new Texture(STRING_FIELD_TEXTURE));
            Field f = new Field(i, STRING_FIELD_NAME_PREFIX + i, model);
            fields.add(f);
        }

        map.setFields(fields);

        for (Field f : map.getFields()) {
            f.getModel().draw(batch);
        }
    }

    private void createMap() {
        map = new Map();
        map.setModel(new Model(screenWidth, screenHeight, 0, 0, new Texture(STRING_MAP_TEXTURE)));
        map.getModel().draw(batch);
    }



    @Override
    public void dispose() {
        batch.dispose();
    }

}