package aau.se2.glock.alpha.gameoflife.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.Player;

/**
 *
 */
public class MainMenuScreen implements Screen {

    /**
     *
     */
    private final OrthographicCamera gameCamera;

    /**
     *
     */
    private final Viewport gameViewPort;

    /**
     *
     */
    private int screenWidth, screenHeight, centerWidth, centerHeight;

    /**
     *
     */
    private int buttonWidth, buttonHeight;

    /**
     *
     */
    private Vector2 buttonPosition;

    /**
     *
     */
    private Stage stage;

    /**
     *
     */
    private TextButton btnStartGame, btnJoinGame;

    /**
     *
     */
    private TextButtonStyle textButtonStyle;

    /**
     *
     */
    private BitmapFont standardFont, bigFont;

    /**
     *
     */
    private Skin skin;

    /**
     *
     */
    private Texture lightGrayTexture, grayTextrue;

    /**
     *
     */
    private TextFieldStyle textFieldStyle;

    /**
     *
     */
    private TextField usernameInput;

    /**
     *
     */
    private NinePatchDrawable borderDrawable;

    /**
     * Constructor for MainMenuScreen. Initializes the screen dimensions, fonts, stage, textures and UI elements.
     */
    public MainMenuScreen() {
        gameCamera = new OrthographicCamera();
        gameViewPort = new StretchViewport(800, 400, gameCamera);

        initScreenDimensions();
        initFonts();
        initStage();
        initTextures();
        createGameOfLifeTitle();
        createUsernameInput();
        createMainMenuButtons();
    }

    @Override
    public void show() {

    }

    /**
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        stage.getBatch().setProjectionMatrix(gameCamera.combined);
        stage.act(Gdx.graphics.getDeltaTime()); // Update the stage
        stage.draw(); // Draw the stage
    }

    /**
     * @param width
     * @param height
     */
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

    /**
     *
     */
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
        Label label = new Label("Game of Life", labelStyle); // Create the label with the text and style
        label.setPosition(centerWidth - (label.getWidth() / 2), centerHeight + (buttonHeight * 2)); // Set the position of the label
        stage.addActor(label); // Add the label to the stage
    }

    /**
     * Creates the username input text field and adds it to the stage.
     */
    private void createUsernameInput() {
        // Create a TextFieldStyle
        textFieldStyle = new TextFieldStyle();
        textFieldStyle.font = standardFont; // Set the font for the text field
        textFieldStyle.fontColor = Color.LIGHT_GRAY; // Set the font color for the text field
        textFieldStyle.cursor = new TextureRegionDrawable(new TextureRegion(new TextureRegion(lightGrayTexture))); // Set the cursor for the text field
        // Create a NinePatchDrawable with a border color
        borderDrawable = new NinePatchDrawable(new NinePatch(new Texture(Gdx.files.internal("border.png"))));
        // Set the border color
        borderDrawable.getPatch().setColor(Color.GRAY);
        // Set the background for the text field
        textFieldStyle.background = borderDrawable;


        // Create the text field using the registered style
        usernameInput = new TextField("", textFieldStyle); // You can set an initial text value in the first parameter of the TextField constructor
        usernameInput.setPosition(centerWidth - (buttonWidth / 2), (float) (buttonPosition.y + (buttonHeight * 1.25))); // Set the position of the text field
        usernameInput.setSize(buttonWidth, buttonHeight); // Set the size of the text field
        // Set the placeholder text
        usernameInput.setMessageText("Enter username"); // Set the placeholder text

        if (GameOfLife.self != null && !GameOfLife.self.getUsername().isEmpty()) {
            usernameInput.setText(GameOfLife.self.getUsername());
        }

        textFieldStyle.background.setLeftWidth(screenWidth / 50); // Set the left padding
        textFieldStyle.background.setRightWidth(screenWidth / 50); // Set the right padding
        textFieldStyle.background.setTopHeight(screenWidth / 50); // Set the top padding
        textFieldStyle.background.setBottomHeight(screenWidth / 50); // Set the bottom padding

        //If there was an error, we want to remove the read marking if the usernameInput text gets valid
        usernameInput.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Check if the length of the text is greater than 0
                if (validateInput(usernameInput)) {
                    // Set the font color to light gray
                    usernameInput.setColor(Color.LIGHT_GRAY);
                    usernameInput.getStyle().fontColor = Color.LIGHT_GRAY;
                }
            }
        });

        stage.addActor(usernameInput); // Add the text field to the stage
    }

    /**
     * Creates the main menu buttons and adds them to the stage.
     */
    private void createMainMenuButtons() {
        //create a textButtonStyle
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(grayTextrue)); // Set the up state texture
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(lightGrayTexture)); // Set the down state texture
        textButtonStyle.font = standardFont; // Set the font
        textButtonStyle.fontColor = Color.WHITE; // Set the font color

        //Create a Start Game Button
        btnStartGame = new TextButton("Start Game", textButtonStyle); // Create the text button with the text and style
        btnStartGame.setPosition(buttonPosition.x, buttonPosition.y); // Set the position of the button
        btnStartGame.setSize(buttonWidth, buttonHeight); // Set the size of the button

        //Create a Join Game Button
        btnJoinGame = new TextButton("Join Game", textButtonStyle); // Create the text button with the text and style
        btnJoinGame.setPosition(buttonPosition.x, (float) (buttonPosition.y - (buttonHeight * 1.25)));
        btnJoinGame.setSize(buttonWidth, buttonHeight);

        stage.addActor(btnStartGame); // Add the button to the stage
        stage.addActor(btnJoinGame);

        btnJoinGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (validateInput(usernameInput)) {
                    GameOfLife.self = new Player(usernameInput.getText(), false);
                    GameOfLife.players = new ArrayList<>();
                    GameOfLife.players.add(GameOfLife.self);

                    //GameOfLife.server.start(GameOfLife.self.getUsername());

                    GameOfLife.changeScreen(new JoinGameScreen());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            GameOfLife.client.discoverServers(GameOfLife.UDPPORT);
                            System.out.println(GameOfLife.availableServers);
                            //GameOfLife.getInstance().render();
                        }
                    }).start();
                }
            }
        });

        //Create a ClickListener
        //When there is no input in the usernameInput then we show an error
        btnStartGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (validateInput(usernameInput)) {
                    GameOfLife.self = new Player(usernameInput.getText(), true);
                    GameOfLife.players = new ArrayList<>();
                    GameOfLife.players.add(GameOfLife.self);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            GameOfLife.server.start(GameOfLife.self.getUsername());
                            try {
                                GameOfLife.client.connect(InetAddress.getByName("localhost"), GameOfLife.TCPPORT, GameOfLife.UDPPORT);
                            } catch (UnknownHostException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }).start();

                    GameOfLife.changeScreen(new StartGameScreen());
                }
            }
        });
    }

    /**
     * Checks if the input text is valid (length > 0).
     * Changes color of TextField, dependent on validation result.
     *
     * @param txtField TextField object to validate and manipulate
     * @return true if input is valid, false if not
     */
    private boolean validateInput(TextField txtField) {
        if (txtField.getText().length() < 1) {
            txtField.setColor(Color.RED);
            txtField.getStyle().messageFontColor = Color.RED;
            return false;
        }

        return true;
    }
}