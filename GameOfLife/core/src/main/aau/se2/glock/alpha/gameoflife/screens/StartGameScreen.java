package aau.se2.glock.alpha.gameoflife.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.Player;

public class StartGameScreen implements Screen {
    private OrthographicCamera gameCamera;
    private Viewport gameViewPort;
    private int screenWidth, screenHeight, centerWidth, centerHeight;
    private int buttonWidth, buttonHeight;
    public Vector2 buttonPosition;
    private Stage stage;
    private TextButton btnStartGame;
    private TextButton btnBack;
    private TextButtonStyle textButtonStyle;
    private Skin skin;
    private Texture lightGrayTexture, grayTextrue;
    private Label label;
    private BitmapFont standardFont, bigFont;

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
        createBackButton();
        createInfoLabel();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        stage.getBatch().setProjectionMatrix(gameCamera.combined);
        stage.act(Gdx.graphics.getDeltaTime()); // Update the stage
        stage.draw(); // Draw the stage
    }

    @Override
    public void resize(int width, int height) {
        gameViewPort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    public void dispose() {

    }

    @Override
    public void hide() {
        this.dispose();
    }

    /**
     * Initializes the screen dimensions such as screen width, screen height, button width and button height.
     */
    private void initScreenDimensions() {
        screenWidth = Gdx.graphics.getWidth();
        centerWidth = screenWidth / 2;
        screenHeight = Gdx.graphics.getHeight();
        centerHeight = screenHeight / 2;

        buttonWidth = screenWidth / 5;
        buttonHeight = screenHeight / 8;

        buttonPosition = new Vector2(centerWidth - (buttonWidth / 2), centerHeight - buttonHeight);
    }

    /**
     * Initializes the fonts used in the UI elements.
     */
    private void initFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Accuratist.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36;
        standardFont = generator.generateFont(parameter);
        parameter.size = 128;
        bigFont = generator.generateFont(parameter);
        generator.dispose();
    }

    /**
     * Initializes the stage for handling UI elements.
     */
    private void initStage() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin();
    }

    /**
     * Initializes the textures used for UI elements.
     */
    private void initTextures() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);

        pixmap.setColor(Color.LIGHT_GRAY);
        pixmap.fill();
        lightGrayTexture = new Texture(pixmap);

        pixmap.setColor(Color.GRAY);
        pixmap.fill();
        grayTextrue = new Texture(pixmap);

        pixmap.dispose();
    }

    /**
     * Creates the Game of Life title as a label and adds it to the stage.
     */
    private void createGameOfLifeTitle() {
        //Create Game of Life Title
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = bigFont; // Set the font for the label
        labelStyle.fontColor = Color.WHITE; // Set the font color for the label
        label = new Label("Game of Life", labelStyle); // Create the label with the text and style
        label.setPosition(centerWidth - (label.getWidth() / 2), centerHeight + (buttonHeight * 2)); // Set the position of the label
        stage.addActor(label); // Add the label to the stage
    }

    private void createPlayersOverview() {
        //Create Overview for Players
        Label.LabelStyle labelPlayerStyle = new Label.LabelStyle();
        labelPlayerStyle.font = standardFont; // Set the font for the label
        labelPlayerStyle.fontColor = Color.WHITE; // Set the font color for the label
        Label labelPlayers = new Label("Players", labelPlayerStyle); // Create the label with the text and style
        labelPlayers.setPosition(centerWidth - (label.getWidth() / 2), centerHeight + (buttonHeight * 2) - (standardFont.getXHeight() * 2f)); // Set the position of the label
        stage.addActor(labelPlayers); // Add the label to the stage

        int count = 0;
        for (Player player : GameOfLife.players) {
            Gdx.app.log("count", count + "");
            Label labelPlayer = new Label(player.getUsername(), labelPlayerStyle); // Create the label with the text and style
            labelPlayer.setPosition(centerWidth - (label.getWidth() / 2) + (labelPlayers.getWidth() / 2), centerHeight + (buttonHeight * 2) - (standardFont.getXHeight() * 2.0f) - (standardFont.getXHeight() * (count + 2.5f))); // Set the position of the label
            stage.addActor(labelPlayer); // Add the label to the stage
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
                    GameOfLife.changeScreen(new GameScreen());
                }
            };

            btnStartGame.addListener(btnStartGameListener);
        }
    }

    private void createBackButton() {
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
}