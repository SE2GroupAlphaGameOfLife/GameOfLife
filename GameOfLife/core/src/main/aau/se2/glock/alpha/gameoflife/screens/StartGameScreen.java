package aau.se2.glock.alpha.gameoflife.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.ArrayList;
import java.util.List;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.Player;
import aau.se2.glock.alpha.gameoflife.networking.client.ClientClass;
import aau.se2.glock.alpha.gameoflife.networking.server.ServerClass;

/**
 *
 */
public class StartGameScreen extends BasicScreen {

    private final TextButtonStyle textButtonStyle;
    private final List<Label> playerLabels = new ArrayList<>();
    private TextButton btnStartGame;
    private TextButton btnBack;

    public StartGameScreen() {
        gameCamera = new OrthographicCamera();
        gameViewPort = new StretchViewport(800, 400, gameCamera);

        initScreenDimensions();
        initFonts();
        initStage();
        initTextures();

        //create a textButtonStyle
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(grayTextrue)); // Set the up state texture
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(lightGrayTexture)); // Set the down state texture
        textButtonStyle.font = standardFont; // Set the font
        textButtonStyle.fontColor = Color.WHITE; // Set the font color

        createGameOfLifeTitle();
        createPlayersOverview();
        createStartGameButton();
        createQuitButton();
        createInfoLabel();
    }

    public void createPlayersOverview() {
        // Remove old server labels from the stage
        for (Label oldPlayerLabel : playerLabels) {
            oldPlayerLabel.remove();
        }

        // Clear the serverLabels list
        playerLabels.clear();

        //Create Overview for Players
        Label.LabelStyle labelPlayerStyle = new Label.LabelStyle();
        labelPlayerStyle.font = standardFont; // Set the font for the label
        labelPlayerStyle.fontColor = Color.WHITE; // Set the font color for the label
        Label labelPlayers = new Label("Players", labelPlayerStyle); // Create the label with the text and style
        labelPlayers.setPosition(centerWidth - (label.getWidth() / 2), centerHeight + (buttonHeight * 2) - (standardFont.getXHeight() * 2f)); // Set the position of the label
        stage.addActor(labelPlayers); // Add the label to the stage
        playerLabels.add(labelPlayers);

        Label labelPlayer = new Label("", labelPlayerStyle);

        int count = 0;
        for (Player player : GameOfLife.players) {
            Gdx.app.log("count", count + "");
            labelPlayer = new Label(player.isHost() ? (player.getUsername() + " (Host)") : player.getUsername(), labelPlayerStyle); // Create the label with the text and style
            labelPlayer.setPosition(centerWidth - (label.getWidth() / 2) + (labelPlayers.getWidth() / 2), centerHeight + (buttonHeight * 2) - (standardFont.getXHeight() * 2.0f) - (standardFont.getXHeight() * (count * 2.5f + 2.5f))); // Set the position of the label
            stage.addActor(labelPlayer); // Add the label to the stage
            playerLabels.add(labelPlayer);
            count++;
        }
    }

    private void createStartGameButton() {
        //Create a Start Game Button
        if (GameOfLife.self.isHost()) {
            btnStartGame = new TextButton("Start Game", textButtonStyle); // Create the text button with the text and style
            btnStartGame.setPosition(buttonPosition.x, (float) (buttonPosition.y - (buttonHeight * 1.25))); // Set the position of the button
            btnStartGame.setSize(buttonWidth, buttonHeight); // Set the size of the button

            stage.addActor(btnStartGame); // Add the button to the stage

            // Create a ClickListener
            ClickListener btnStartGameListener = new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // This method will be called when the TextButton is clicked

                    /*
                    THIS IS FOR TESTING ONLY AND HAS TO BE REMOVED ON FINAL VERSION
                     */
                    btnStartGame.setVisible(false);
                    Gdx.app.log("StartGameScreen", "StartGame button pressed!");
                    GameOfLife.client.sendMessageToServerTCP(GameOfLife.START_GAME_PAYLOAD);
                    /*
                    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                     */
                    if ((GameOfLife.players.size() >= 1) && (GameOfLife.players.size() == GameOfLife.server.getPlayers().getPlayerCount())) {
                        //Broadcast, game started
                        btnStartGame.setVisible(false);
                        Gdx.app.log("StartGameScreen", "StartGame button pressed!");
                        GameOfLife.client.sendMessageToServerTCP(GameOfLife.START_GAME_PAYLOAD);
                    } else {
                        //Should be shown to user, that at least 2 players have to have joined or that some player joining is in progress
                    }
                }
            };

            btnStartGame.addListener(btnStartGameListener);
        }
    }

    private void createQuitButton() {
        //Create a Back Button
        btnBack = new TextButton("back", textButtonStyle); // Create the text button with the text and style
        btnBack.setSize(buttonWidth, buttonHeight); // Set the size of the button
        btnBack.setPosition(30, 30); // Set the position of the button

        stage.addActor(btnBack); // Add the button to the stage

        // Create a ClickListener
        ClickListener btnBackListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // This method will be called when the TextButton is clicked
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (GameOfLife.self.isHost()) {
                            GameOfLife.server.close();
                        } else {
                            GameOfLife.client.disconnect();
                        }
                    }
                }).start();
                GameOfLife.changeScreen(new MainMenuScreen());
            }
        };

        btnBack.addListener(btnBackListener);
    }

    private void createInfoLabel() {
        if (!GameOfLife.self.isHost()) {
            Label.LabelStyle labelStyle = new Label.LabelStyle();
            labelStyle.font = standardFont; // Set the font for the label
            labelStyle.fontColor = Color.WHITE; // Set the font color for the label
            label = new Label("Waiting for host to start the game...", labelStyle); // Create the label with the text and style
            label.setPosition(buttonPosition.x, (float) (buttonPosition.y - (buttonHeight * 1.25)));
            stage.addActor(label); // Add the label to the stage]
        }
    }

    @Override
    public void update(String payload) {
        if (payload.equals(GameOfLife.START_GAME_PAYLOAD)) {
            Gdx.app.log("StartGameScreen/update", "StartGamePayload received!");
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    // code to be executed on main thread
                    GameOfLife.changeScreen(new GameScreen());
                }
            });
        } else if (payload.equals(GameOfLife.CREATE_PLAYERS_OVERVIEW_PAYLOAD)) {
            this.createPlayersOverview();
        } else if (payload.equals(GameOfLife.clientConnectingFailed)) {
            Gdx.app.postRunnable(new Runnable() {
                @Override
                public void run() {
                    // code to be executed on main thread
                    GameOfLife.changeScreen(new MainMenuScreen());
                }
            });
        }
    }
}