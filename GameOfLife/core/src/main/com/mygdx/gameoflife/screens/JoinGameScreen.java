package com.mygdx.gameoflife.screens;

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
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.gameoflife.core.AvailableServerDetails;
import com.mygdx.gameoflife.GameOfLife;

import java.util.ArrayList;
import java.util.List;

public class JoinGameScreen implements Screen {

    private OrthographicCamera gameCamera;
    private Viewport gameViewPort;
    private int screenWidth, screenHeight, centerWidth, centerHeight;
    private int btnJoinWidth, btnJoinHeight;
    public Vector2 btnJoinPosition;
    private Stage stage;
    private TextButton btnJoinGame;
    private TextButton.TextButtonStyle textButtonStyle;
    private Skin skin;
    private Texture lightGrayTexture, grayTextrue;
    private BitmapFont standardFont, bigFont;
    private TextField.TextFieldStyle textFieldStyle;
    private TextField ipInput;
    private NinePatch borderPatch;

    public JoinGameScreen() {
        gameCamera = new OrthographicCamera();
        gameViewPort = new StretchViewport(800, 400, gameCamera);

        initScreenDimensions();
        initFonts();
        initStage();
        initTextures();

        //create a textButtonStyle
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(grayTextrue)); // Set the up state texture
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(lightGrayTexture)); // Set the down state texture
        textButtonStyle.font = standardFont; // Set the font
        textButtonStyle.fontColor = Color.WHITE; // Set the font color

        //create mock data
        List<AvailableServerDetails> serverDetails = new ArrayList<>();
        serverDetails.add(new AvailableServerDetails("Host1", "0.0.0.1"));
        serverDetails.add(new AvailableServerDetails("Host2", "0.0.0.2"));
        serverDetails.add(new AvailableServerDetails("Host3", "0.0.0.3"));
        serverDetails.add(new AvailableServerDetails("Host4", "0.0.0.4"));
        serverDetails.add(new AvailableServerDetails("Host5", "0.0.0.5"));
        serverDetails.add(new AvailableServerDetails("Host6", "0.0.0.6"));
        GameOfLife.availableServerDetails = serverDetails;
        // ----

        createServerTextField();
        createJoinGameButton();
        createServerOverview();
    }

    private void createServerTextField() {
        // Create a TextFieldStyle
        textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = standardFont; // Set the font for the text field
        textFieldStyle.fontColor = Color.LIGHT_GRAY; // Set the font color for the text field
        textFieldStyle.cursor = new TextureRegionDrawable(new TextureRegion(new TextureRegion(lightGrayTexture))); // Set the cursor for the text field
        textFieldStyle.background = new NinePatchDrawable(createBorderPatch());

        // Create the text field using the registered style
        ipInput = new TextField("", textFieldStyle); // You can set an initial text value in the first parameter of the TextField constructor
        ipInput.setSize(screenWidth - btnJoinWidth - screenWidth / 25 * 2 - screenWidth / 70, btnJoinHeight); // Set the size of the text field
        ipInput.setPosition(screenWidth / 25, screenHeight - screenHeight / 25 - ipInput.getHeight()); // Set the position of the text field
        // Set the placeholder text
        ipInput.setMessageText("Enter IP-Address"); // Set the placeholder text

        textFieldStyle.background.setLeftWidth(screenWidth / 50); // Set the left padding
        textFieldStyle.background.setRightWidth(screenWidth / 50); // Set the right padding
        textFieldStyle.background.setTopHeight(screenWidth / 50); // Set the top padding
        textFieldStyle.background.setBottomHeight(screenWidth / 50); // Set the bottom padding

        //If there was an error, we want to remove the read marking if the ipInput text gets valid
        ipInput.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Check if the length of the text is greater than 0
                if (validateInput(ipInput.getText())) {
                    // Set the font color to light gray
                    ipInput.setColor(Color.LIGHT_GRAY);
                    ipInput.getStyle().fontColor = Color.LIGHT_GRAY;
                }
            }
        });

        stage.addActor(ipInput); // Add the text field to the stage
    }

    private boolean validateInput(String input) {
        //check for valid ip-address
        return true;
    }

    private void createJoinGameButton() {
        //create a textButtonStyle
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(grayTextrue)); // Set the up state texture
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(lightGrayTexture)); // Set the down state texture
        textButtonStyle.font = standardFont; // Set the font
        textButtonStyle.fontColor = Color.WHITE; // Set the font color

        //Create a Join Game Button
        btnJoinGame = new TextButton("Join", textButtonStyle); // Create the text button with the text and style
        btnJoinGame.setSize(btnJoinWidth, btnJoinHeight);
        btnJoinGame.setPosition(ipInput.getX() + ipInput.getWidth() + screenWidth / 70, ipInput.getY());

        stage.addActor(btnJoinGame);

        //Create a ClickListener
        //When there is no input in the usernameInput then we show an error
        btnJoinGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameOfLife.changeScreen(new GameScreen());
            }
        });
    }

    private void createServerOverview() {
        //Create Overview for available Servers
        Label.LabelStyle labelServerDetailStyle = new Label.LabelStyle();
        labelServerDetailStyle.font = standardFont; // Set the font for the label
        labelServerDetailStyle.fontColor = Color.WHITE; // Set the font color for the label
        Label labelServers = new Label("Available Servers:", labelServerDetailStyle); // Create the label with the text and style
        labelServers.setPosition(screenWidth / 25, screenHeight - screenHeight / 25 * 2 - ipInput.getHeight()); // Set the position of the label
        stage.addActor(labelServers); // Add the label to the stage

        int count = 0;
        for (final AvailableServerDetails serverDetails : GameOfLife.availableServerDetails) {
            Label serverLable = new Label(serverDetails.getHost() + ": " + serverDetails.getIp(), labelServerDetailStyle); // Create the label with the text and style
            serverLable.setPosition(screenWidth/20, labelServers.getY()-screenHeight/25 - count*45); // Set the position of the label
            serverLable.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    onIpClicked(serverDetails.getIp());
                }
            });
            stage.addActor(serverLable); // Add the label to the stage
            count++;
        }
    }

    private void onIpClicked(String ipAddress) {
        ipInput.setText(ipAddress);
    }

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

    private void initStage() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin();
    }

    private void initFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Accuratist.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36;
        standardFont = generator.generateFont(parameter);
        parameter.size = 128;
        bigFont = generator.generateFont(parameter);
        generator.dispose();
    }

    private void initScreenDimensions() {
        screenWidth = Gdx.graphics.getWidth();
        centerWidth = screenWidth / 2;
        screenHeight = Gdx.graphics.getHeight();
        centerHeight = screenHeight / 2;

        btnJoinWidth = screenWidth / 5;
        btnJoinHeight = screenHeight / 13;

        btnJoinPosition = new Vector2(centerWidth - (btnJoinWidth / 2), centerHeight - btnJoinHeight);
    }

    private NinePatch createBorderPatch() {
        int borderSize = 3;
        int patchSize = borderSize * 2 + 1;

        Pixmap pixmap = new Pixmap(patchSize, patchSize, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GRAY);
        pixmap.fillRectangle(0, 0, pixmap.getWidth(), borderSize);
        pixmap.fillRectangle(0, pixmap.getHeight() - borderSize, pixmap.getWidth(), borderSize);
        pixmap.fillRectangle(0, 0, borderSize, pixmap.getHeight());
        pixmap.fillRectangle(pixmap.getWidth() - borderSize, 0, borderSize, pixmap.getHeight());

        NinePatch patch = new NinePatch(new Texture(pixmap), borderSize, borderSize, borderSize, borderSize);
        pixmap.dispose();

        return patch;
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

    @Override
    public void hide() {
        this.dispose();
    }

    @Override
    public void dispose() {

    }
}
