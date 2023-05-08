package aau.se2.glock.alpha.gameoflife.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.networking.packages.ServerInformation;

public class JoinGameScreen implements Screen {

    private OrthographicCamera gameCamera;
    private Viewport gameViewPort;
    private int screenWidth, screenHeight, centerWidth, centerHeight;
    private int btnWidth, btnHeight;
    public Vector2 btnJoinPosition;
    private Stage stage;
    private TextButton btnJoinGame;
    private TextButton btnBack;

    private TextButton.TextButtonStyle textButtonStyle;
    private Skin skin;
    private Texture lightGrayTexture, grayTextrue;
    private BitmapFont standardFont, bigFont;
    private TextField.TextFieldStyle textFieldStyle;
    private TextField ipInput;
    private TextureRegion refreshIcon;
    private TextureRegion transparentImage;
    private boolean showRefreshIcon;
    private float rotationSpeed = 180; // degrees per second
    private float currentRotation = 0f;
    private float originXRefreshIcon=0f;
    private float originYRefreshIcon=0f;

    private List<Label> serverLabels = new ArrayList<>();

    private final Timer timer;


    public JoinGameScreen() {
        gameCamera = new OrthographicCamera();
        gameViewPort = new StretchViewport(800, 400, gameCamera);

        timer = new Timer();

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

        createServerTextField();
        createJoinGameButton();
        createServerOverview();
        createBackButton();

        Texture refreshIconTexture = new Texture("refresh.png");
        refreshIcon = new TextureRegion(refreshIconTexture);

        createTransparentImage();
        showRefreshIcon = false;

        refreshImageInterval();
    }

    private void createTransparentImage() {
        Pixmap pixmap = new Pixmap(refreshIcon.getRegionWidth(), refreshIcon.getRegionHeight(), Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0); // set the color to transparent
        pixmap.fill();
        Texture transparentTexture = new Texture(pixmap);
        transparentImage = new TextureRegion(transparentTexture);
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
        ipInput.setSize(screenWidth - btnWidth - screenWidth / 25 * 2 - screenWidth / 70, btnHeight); // Set the size of the text field
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

    private boolean validateInput(String ipAddress) {
        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            return inetAddress instanceof Inet4Address;
        } catch (UnknownHostException ex) {
            return false;
        }
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
        btnJoinGame.setSize(btnWidth, btnHeight);
        btnJoinGame.setPosition(ipInput.getX() + ipInput.getWidth() + screenWidth / 70, ipInput.getY());

        stage.addActor(btnJoinGame);

        //Create a ClickListener
        //When there is no input in the usernameInput then we show an error
        btnJoinGame.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {


                GameOfLife.changeScreen(new StartGameScreen());

            }
        });
    }

    private void createServerOverview() {
        // Remove old server labels from the stage
        for (Label oldServerLabel : serverLabels) {
            oldServerLabel.remove();
        }
        // Clear the serverLabels list
        serverLabels.clear();

        //Create Overview for available Servers
        Label.LabelStyle labelServerDetailStyle = new Label.LabelStyle();
        labelServerDetailStyle.font = standardFont; // Set the font for the label
        labelServerDetailStyle.fontColor = Color.WHITE; // Set the font color for the label
        Label labelServers = new Label("Available Servers:", labelServerDetailStyle); // Create the label with the text and style
        labelServers.setPosition(screenWidth / 25, screenHeight - screenHeight / 25 * 2 - ipInput.getHeight()); // Set the position of the label
        stage.addActor(labelServers); // Add the label to the stage

        serverLabels.add(labelServers);

        int count = 0;
        Label serverLabel = new Label("", labelServerDetailStyle); // Create the label with the text and style

        if (GameOfLife.availableServers.isEmpty()) {
            serverLabel = new Label("Searching for servers...", labelServerDetailStyle); // Create the label with the text and style
            serverLabel.setPosition(screenWidth / 20, labelServers.getY() - screenHeight / 25 - count * 45); // Set the position of the label
            stage.addActor(serverLabel);
            serverLabels.add(serverLabel); // Add the label to the serverLabels list
            count++;
        } else {
            for (final ServerInformation serverDetails : GameOfLife.availableServers) {
                serverLabel = new Label(serverDetails.getHostname() + ": " + serverDetails.getAddress(), labelServerDetailStyle); // Create the label with the text and style
                serverLabel.setPosition(screenWidth / 20, labelServers.getY() - screenHeight / 25 - count * 45); // Set the position of the label
                serverLabel.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        onIpClicked(serverDetails.getAddress());
                    }
                });
                stage.addActor(serverLabel);
                serverLabels.add(serverLabel); // Add the label to the serverLabels list
                count++;
            }
        }
    }

    private void onIpClicked(InetAddress ipAddress) {
        ipInput.setText(ipAddress.getHostAddress());
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

        btnWidth = screenWidth / 5;
        btnHeight = screenHeight / 13;

        btnJoinPosition = new Vector2(centerWidth - (btnWidth / 2), centerHeight - btnHeight);
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

    private void createBackButton() {
        //Create a Back Button
        btnBack = new TextButton("back", textButtonStyle); // Create the text button with the text and style
        btnBack.setSize(btnWidth, btnHeight); // Set the size of the button
        btnBack.setPosition(30, 30); // Set the position of the button

        stage.addActor(btnBack); // Add the button to the stage

        // Create a ClickListener
        ClickListener btnBackListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // This method will be called when the TextButton is clicked
                timer.clear();
                GameOfLife.changeScreen(new MainMenuScreen());
            }
        };

        btnBack.addListener(btnBackListener);
    }

    public void refreshImageInterval() {
        createRotation();
        
        final float showTime = 1f; // in seconds
        final float hideTime = 5f; // in seconds

        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                showRefreshIcon = true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        refreshServerList();
                    }
                }).start(); //-> refreshServerList is called, but icon is not hiding anymore...
                timer.scheduleTask(new Timer.Task() {
                    @Override
                    public void run() {
                        showRefreshIcon = false;
                    }
                }, showTime);
            }
        }, 0, showTime + hideTime); // schedule the task to repeat after showTime + hideTime seconds
    }

    private void createRotation() {

        originXRefreshIcon = refreshIcon.getRegionWidth() * 0.2f / 2;
        originYRefreshIcon = refreshIcon.getRegionHeight() * 0.2f / 2;
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                currentRotation += rotationSpeed * Gdx.graphics.getDeltaTime();
                currentRotation %= 360; // keep the angle between 0 and 360 degrees
            }
        }, 0, 0.01f); // schedule the task to run every 0.01 seconds
    }

    private void refreshServerList() {
        GameOfLife.client.discoverServers(GameOfLife.UDPPORT);
        this.createServerOverview();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        stage.getBatch().setProjectionMatrix(gameCamera.combined);
        stage.act(Gdx.graphics.getDeltaTime()); // Update the stage
        stage.draw();

        if (showRefreshIcon) {
            stage.getBatch().begin();
            stage.getBatch().draw(refreshIcon, (float) (screenWidth - refreshIcon.getRegionWidth() * 0.2 - 10), 10F,originXRefreshIcon, originYRefreshIcon, (float) (refreshIcon.getRegionWidth() * 0.2), (float) (refreshIcon.getRegionHeight() * 0.2), 1, 1, currentRotation);
            stage.getBatch().end();
        } else {
            stage.getBatch().begin();
            stage.getBatch().draw(transparentImage, screenWidth - transparentImage.getRegionWidth() - 10, 10);
            stage.getBatch().end();
        }
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
