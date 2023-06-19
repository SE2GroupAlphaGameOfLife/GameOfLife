package aau.se2.glock.alpha.gameoflife.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.Player;

/**
 *
 */
public class MainMenuScreen extends BasicScreen {

    private TextButton btnHostGame, btnJoinGame;
    private TextButtonStyle textButtonStyle;
    private TextFieldStyle textFieldStyle;
    private TextField usernameInput;
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
        usernameInput.setPosition((float) centerWidth - ((float) buttonWidth / 2), (float) (buttonPosition.y + (buttonHeight * 1.25F))); // Set the position of the text field
        usernameInput.setSize(buttonWidth, buttonHeight); // Set the size of the text field
        // Set the placeholder text
        usernameInput.setMessageText("Enter username"); // Set the placeholder text

        if (GameOfLife.self != null && GameOfLife.self.getUsername() != null && !GameOfLife.self.getUsername().isEmpty()) {
            usernameInput.setText(GameOfLife.self.getUsername());
        }

        textFieldStyle.background.setLeftWidth((float) screenWidth / 50); // Set the left padding
        textFieldStyle.background.setRightWidth((float) screenWidth / 50); // Set the right padding
        textFieldStyle.background.setTopHeight((float) screenWidth / 50); // Set the top padding
        textFieldStyle.background.setBottomHeight((float) screenWidth / 50); // Set the bottom padding

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
        btnHostGame = new TextButton("Host Game", textButtonStyle); // Create the text button with the text and style
        btnHostGame.setPosition(buttonPosition.x, buttonPosition.y); // Set the position of the button
        btnHostGame.setSize(buttonWidth, buttonHeight); // Set the size of the button

        //Create a Join Game Button
        btnJoinGame = new TextButton("Join Game", textButtonStyle); // Create the text button with the text and style
        btnJoinGame.setPosition(buttonPosition.x, (float) (buttonPosition.y - (buttonHeight * 1.25)));
        btnJoinGame.setSize(buttonWidth, buttonHeight);

        stage.addActor(btnHostGame); // Add the button to the stage
        stage.addActor(btnJoinGame);

        btnJoinGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (validateInput(usernameInput)) {
                    GameOfLife.self = new Player(usernameInput.getText(), false);
                    GameOfLife.players = new ArrayList<>();
                    GameOfLife.players.add(GameOfLife.self);

                    GameOfLife.changeScreen(new JoinGameScreen());
                    //GameOfLife.client.discoverServers(GameOfLife.UDPPORT);
                }
            }
        });

        //Create a ClickListener
        //When there is no input in the usernameInput then we show an error
        btnHostGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (validateInput(usernameInput)) {
                    GameOfLife.self = new Player(usernameInput.getText(), true);
                    GameOfLife.players = new ArrayList<>();
                    GameOfLife.players.add(GameOfLife.self);

                    if (!GameOfLife.server.isServerStarted()) {

                        GameOfLife.changeScreen(new StartGameScreen());

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                GameOfLife.server.start(GameOfLife.self.getUsername());
                                try {
                                    GameOfLife.client.connect(InetAddress.getByName("localhost"), GameOfLife.TCPPORT, GameOfLife.UDPPORT);
                                } catch (UnknownHostException e) {
                                    GameOfLife.changeScreen(new MainMenuScreen());
                                    //throw new RuntimeException(e);
                                }
                            }
                        }).start();
                    } else {
                        GameOfLife.server.close();
                    }
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

    @Override
    public void update(String payload) {
        if (payload.equals(GameOfLife.CLIENT_CONNECTION_FAILED_PAYLOAD)) {
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