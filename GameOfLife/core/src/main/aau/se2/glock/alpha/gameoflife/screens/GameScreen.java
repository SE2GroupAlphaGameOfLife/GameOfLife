package aau.se2.glock.alpha.gameoflife.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.ArrayList;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.Board;
import aau.se2.glock.alpha.gameoflife.core.GameField;
import aau.se2.glock.alpha.gameoflife.core.Player;
import aau.se2.glock.alpha.gameoflife.core.jobs.Job;
import aau.se2.glock.alpha.gameoflife.core.jobs.JobData;
import aau.se2.glock.alpha.gameoflife.core.utilities.ProximityListener;
import aau.se2.glock.alpha.gameoflife.networking.packages.ServerInformation;
import aau.se2.glock.alpha.gameoflife.networking.server.ServerClass;

/**
 *
 */
public class GameScreen extends BasicScreen implements ProximityListener {

    private TextButton btnQuit;
    private TextButton btnCheat1Field;
    private TextButton btnCheat2Fields;
    private TextButton btnCheat3Fields;
    private TextButton.TextButtonStyle textButtonStyle;
    private TextButton btnJob;
    private Skin uiSkin;
    private Texture background;
    private Texture skateboard;
    private Button nextFieldButton1;
    private Button nextFieldButton2;
    private Button closeBtn;
    private Button job1Btn;
    private Button job2Btn;
    private Label job1Description;
    private Label job2Description;
    private Group nextFieldButtonGroup; // Create a Group to hold actors
    private Group cheatingButtonGroup;
    private Group spinTheWheelGroup;
    private Group playersGroup;
    private Texture wheelTexture;
    private Drawable wheelDrawable;
    private ImageButton wheelImageButton;
    private ImageButton arrowImageButton;
    private Texture arrowTexture;
    private Label lbUsernameAge;
    private Label lbMoney;
    private Label lbLifepoints;
    private Label.LabelStyle labelStyle;
    private Dialog eventDialog;
    private boolean isSpinning = false;
    private float arrowRotation = 216f; //216 is starting point
    private float spinSpeed = 360f;
    private float maxSpinDuration = 2f;
    private float spinDuration = 0f;
    private float spinAngle = 0f;
    private Image arrowImage;
    private JobData jobSelection;
    private Job[] jobs;

    public GameScreen() {
        jobSelection = JobData.getInstance();
        jobSelection.fillJobList();
        GameOfLife.proximitySensorInterface.setProximityListener(this);

        gameCamera = new OrthographicCamera();
        gameViewPort = new StretchViewport(800, 400, gameCamera);

        initScreenDimensions();
        initFonts();
        initStage();
        initTextures();
        initStyles();
        createQuitButton();
        createPlayerHUD();
        createEventPopup();
        refreshPlayerHUD();
        createJobButton();
    }

    /**
     * Is Triggered, when the proximity sensor has been cover for a specified amount of time.
     */
    @Override
    public void onProximity() {
        // Here, you can define what to do when the proximity sensor is triggered
        Gdx.app.log("Sensor", "Triggered in GameScreen");

        if (GameOfLife.self.hasTurn() && GameOfLife.self.getMoveCount() != 0) {
            createMenuCheating();
        }
    }

    /**
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        stage.getBatch().setProjectionMatrix(gameCamera.combined);

        stage.getBatch().begin();

        stage.getBatch().draw(background, -gameCamera.viewportWidth / 3, -gameCamera.viewportHeight / 2, gameCamera.viewportWidth / 3 * 2, gameCamera.viewportHeight);
        playersGroup.clearChildren();
        for (Player player : GameOfLife.players) {
            GameField currentField = Board.getInstance().getGameFields().get(player.getPosition());
            Vector3 v3 = new Vector3(currentField.getPosition().x - 20, currentField.getPosition().y - 20, 0);
            gameCamera.project(v3);
            ImageButton playerButton = new ImageButton(new TextureRegionDrawable(skateboard));
            playerButton.setPosition(v3.x, v3.y);
            playerButton.setSize(100, 100);
            playersGroup.addActor(playerButton);
        }


        if (GameOfLife.self.hasTurn()) {
            createSpinTheWheelButton();

            if (isSpinning) {
                arrowImage.setRotation(((float) spinAngle + arrowRotation));
            }

            if (!spinTheWheelGroup.hasChildren()) {
                spinTheWheelGroup.addActor(wheelImageButton);
                spinTheWheelGroup.addActor(arrowImageButton);
            }


            if (isSpinning) {
                spinTheWheel(delta);
            }
        } else {
            spinTheWheelGroup.clearChildren();
        }

        stage.getBatch().end();
        stage.act(Gdx.graphics.getDeltaTime()); // Update the stage
        stage.draw(); // Draw the stage
    }

    /**
     * Initialises Styles for Labels,Buttons,ETC...
     */
    private void initStyles() {
        labelStyle = new Label.LabelStyle();
        labelStyle.font = standardFont;
        labelStyle.fontColor = Color.WHITE;

        //create a textButtonStyle
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(grayTextrue)); // Set the up state texture
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(lightGrayTexture)); // Set the down state texture
        textButtonStyle.font = standardFont; // Set the font
        textButtonStyle.fontColor = Color.WHITE; // Set the font color

    }

    /**
     * Initializes the stage for handling UI elements.
     */

    protected void initStage() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        stage.getBatch().setProjectionMatrix(gameCamera.combined);

        nextFieldButtonGroup = new Group();
        cheatingButtonGroup = new Group();
        spinTheWheelGroup = new Group();
        playersGroup = new Group();
        stage.addActor(nextFieldButtonGroup);
        stage.addActor(cheatingButtonGroup);
        stage.addActor(spinTheWheelGroup);
        stage.addActor(playersGroup);
        skin = new Skin();
    }

    /**
     * Initializes the textures used for UI elements.
     */
    protected void initTextures() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);

        pixmap.setColor(Color.LIGHT_GRAY);
        pixmap.fill();
        lightGrayTexture = new Texture(pixmap);

        pixmap.setColor(Color.GRAY);
        pixmap.fill();
        grayTextrue = new Texture(pixmap);

        pixmap.dispose();

        background = new Texture(Gdx.files.internal("board.png"));
        skateboard = new Texture(Gdx.files.internal("skateboard.png"));

        wheelTexture = new Texture("wheel.png");
        arrowTexture = new Texture("arrow.png");

        arrowImage = new Image(arrowTexture);

        wheelDrawable = new TextureRegionDrawable(new TextureRegion(wheelTexture));
        wheelImageButton = new ImageButton(wheelDrawable);
        arrowImageButton = new ImageButton(new TextureRegionDrawable(arrowTexture));
    }

    /**
     * Create Button for rolling the dice.
     */
    private void createSpinTheWheelButton() {
        Vector3 v3Wheel = new Vector3(-50, -50, 0);
        Vector3 v3Arrow = new Vector3(-30, -50, 0);
        Vector3 v3Center = new Vector3(0, 0, 0);
        gameCamera.project(v3Wheel);
        gameCamera.project(v3Center);
        gameCamera.project(v3Arrow);
        wheelImageButton.setSize((v3Center.x - v3Wheel.x) * 2, (v3Center.y - v3Wheel.y) * 2);
        wheelImageButton.setPosition(v3Wheel.x, v3Wheel.y);

        arrowImageButton.setSize((v3Center.x - v3Arrow.x) * 2, (v3Center.y - v3Arrow.y) * 2);
        arrowImageButton.setPosition(v3Arrow.x, v3Arrow.y);
        arrowImage = arrowImageButton.getImage();
        arrowImage.setOrigin(arrowImage.getWidth() / 2, arrowImage.getHeight() / 2);

        // Create a ClickListener
        ClickListener btnRollDiceListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (GameOfLife.self.hasTurn() && GameOfLife.self.getMoveCount() == 0) {
                    // This method will be called when the TextButton is clicked
                    boolean isInTurn = true;

                    //Player player = GameOfLife.self;
                    GameOfLife.self.setAge(GameOfLife.self.getAge() + 1);
                    int moveCount = GameOfLife.self.rollTheDice();
                    Gdx.app.log("moveCount", moveCount + "");

                    //calculating the angle that the arrow has to spin, so that it stops at the correct number
                    float maxSpingAngle = (float) (moveCount * 36) - 18;
                    System.out.println(spinAngle);
                    spinSpeed = ((maxSpingAngle) + 720) / maxSpinDuration;
                    spinAngle = 0;

                    isSpinning = true;
                }
            }
        };

        //btnRollDice.addListener(btnRollDiceListener);
        wheelImageButton.addListener(btnRollDiceListener);
        arrowImageButton.addListener(btnRollDiceListener);
    }


    // !!! JAVA DOC @param BESCHREIBEN !!!

    /**
     * Create Button for rolling the dice.
     */
    private void createMenuCheating() {
        //Create a Start Game Button
        btnCheat1Field = new TextButton("+1 Feld", textButtonStyle); // Create the text button with the text and style
        btnCheat1Field.setPosition(buttonPosition.x, (float) (buttonPosition.y + (buttonHeight * 1.25))); // Set the position of the button
        btnCheat1Field.setSize(buttonWidth, buttonHeight); // Set the size of the button

        cheatingButtonGroup.addActor(btnCheat1Field);

        //Create a Start Game Button
        btnCheat2Fields = new TextButton("+2 Felder", textButtonStyle); // Create the text button with the text and style
        btnCheat2Fields.setPosition(buttonPosition.x, (float) (buttonPosition.y + (buttonHeight * 1.25 * 2))); // Set the position of the button
        btnCheat2Fields.setSize(buttonWidth, buttonHeight); // Set the size of the button

        cheatingButtonGroup.addActor(btnCheat2Fields);

        //Create a Start Game Button
        btnCheat3Fields = new TextButton("+3 Felder", textButtonStyle); // Create the text button with the text and style
        btnCheat3Fields.setPosition(buttonPosition.x, (float) (buttonPosition.y + (buttonHeight * 1.25 * 3))); // Set the position of the button
        btnCheat3Fields.setSize(buttonWidth, buttonHeight); // Set the size of the button

        cheatingButtonGroup.addActor(btnCheat3Fields);

        // Create a ClickListener
        ClickListener btnCheat1FieldListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Player self = GameOfLife.self;

                GameOfLife.self.cheat(1);

                cheatingButtonGroup.clearChildren();
            }

        };
        btnCheat1Field.addListener(btnCheat1FieldListener);

        ClickListener btnCheat2FieldsListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Player self = GameOfLife.self;

                GameOfLife.self.cheat(2);

                cheatingButtonGroup.clearChildren();
            }

        };
        btnCheat2Fields.addListener(btnCheat2FieldsListener);

        ClickListener btnCheat3FieldsListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //Player self = GameOfLife.self;

                GameOfLife.self.cheat(3);

                cheatingButtonGroup.clearChildren();
            }

        };
        btnCheat3Fields.addListener(btnCheat3FieldsListener);
    }

    /**
     * Chooses the next step for the player on the game field.
     *
     * @param gameField The current game field.
     */
    private void chooseNextStep(GameField gameField) {
        //Get the next game field based on player choice (1 or 2)
        GameField nextGameField1 = Board.getInstance().getGameFields().get(gameField.getIndexOfNextGameFields().get(0));

        //Create and configure the button to choose first of the possible next steps
        nextFieldButton1 = new TextButton("Hier", textButtonStyle);
        nextFieldButtonGroup.addActor(nextFieldButton1);
        nextFieldButton1.setSize(100, 50);
        Vector3 v3 = new Vector3(nextGameField1.getPosition().x, nextGameField1.getPosition().y - 5, 0);
        gameCamera.project(v3);
        nextFieldButton1.setPosition(v3.x, v3.y);

        //Add click listener for the first option button
        nextFieldButton1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stepChoosen(0);
            }
        });

        // Get the next game field based on the player's choice (2nd option)
        GameField nextGameField2 = Board.getInstance().getGameFields().get(gameField.getIndexOfNextGameFields().get(1));

        //Create and configure the button to choose second of the possible next steps
        nextFieldButton2 = new TextButton("Hier", textButtonStyle);
        nextFieldButtonGroup.addActor(nextFieldButton2);
        nextFieldButton2.setSize(100, 50);
        v3 = new Vector3(nextGameField2.getPosition().x, nextGameField2.getPosition().y - 5, 0);
        gameCamera.project(v3);
        nextFieldButton2.setPosition(v3.x, v3.y);

        //Add click listener for the second option button
        nextFieldButton2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                stepChoosen(1);
            }
        });
    }

    /**
     * When the player has chosen which step he wants to take next, this function is called.
     * We set the current position of the player to the chosen field and makeMove is called again
     *
     * @param index a GameField has an array of next possible GameFields, index defines the index of the array which we want to choose for our next step
     */
    private void stepChoosen(int index) {
        // Update player's choice and position
        //Player player = GameOfLife.self;
        GameOfLife.self.chooseDirection(index);
        nextFieldButtonGroup.clearChildren();

        //Check if player can still move
        GameField gameField = Board.getInstance().getGameFields().get(GameOfLife.self.getPosition());
        if (!GameOfLife.self.makeMove()) {
            gameField = Board.getInstance().getGameFields().get(GameOfLife.self.getPosition());
            chooseNextStep(gameField);
        }

        if (GameOfLife.self.getMoveCount() == 0) {
            showEventPopUp(GameOfLife.self.getEvent().getText());
            //GameOfLife.client.sendPlayerTCP(GameOfLife.self);
        }
    }

    /**
     * @param delta
     */
    private void spinTheWheel(float delta) {
        // increase spin duration and angle
        spinDuration += delta;
        spinAngle -= spinSpeed * delta;

        // select a random section once spin duration exceeds maximum duration
        if (spinDuration > maxSpinDuration) {
            spinDuration = 0f;
            spinSpeed = 0f;
            isSpinning = false;

            //Player player = GameOfLife.self;
            GameField gameField = Board.getInstance().getGameFields().get(GameOfLife.self.getPosition());
            if (!GameOfLife.self.makeMove()) {
                gameField = Board.getInstance().getGameFields().get(GameOfLife.self.getPosition());

                chooseNextStep(gameField);
            } else {
                if (GameOfLife.self.getMoveCount() == 0) {
                    Gdx.app.log("Zeile", "673");
                    showEventPopUp(GameOfLife.self.getEvent().getText());
                }
            }
        }
    }

    /**
     *
     */
    private void createQuitButton() {
        //Create a Back Button
        btnQuit = new TextButton("quit", textButtonStyle); // Create the text button with the text and style
        btnQuit.setSize(buttonWidth, buttonHeight); // Set the size of the button
        btnQuit.setPosition(30, 30); // Set the position of the button

        stage.addActor(btnQuit); // Add the button to the stage

        // Create a ClickListener
        ClickListener btnQuitListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        GameOfLife.server.close();
                        GameOfLife.server = new ServerClass(GameOfLife.TCPPORT, GameOfLife.UDPPORT);
                    }
                }).start();
                GameOfLife.gameStarted = false;
                GameOfLife.players = new ArrayList<Player>();
                GameOfLife.availableServers = new ArrayList<ServerInformation>();
                GameOfLife.changeScreen(new MainMenuScreen());
            }
        };

        btnQuit.addListener(btnQuitListener);
    }

    /**
     *
     */
    private void createJobButton() {
        btnJob = new TextButton("Job", textButtonStyle);
        btnJob.setSize(buttonWidth, buttonHeight);
        btnJob.setPosition(Gdx.graphics.getWidth() - buttonWidth - 10f, Gdx.graphics.getHeight() - buttonHeight - 10f);

        stage.addActor(btnJob);

        ClickListener btnJobListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("TestJobBtn", "Works");
                chooseJobWindow();
            }
        };

        btnJob.addListener(btnJobListener);
    }


    private void createJobWindow() {
        //loads uiSkin from files
        uiSkin = new Skin(Gdx.files.internal("uiskin.json"));

        final Window window = new Window("", uiSkin);
        window.setSize(600, 450);
        window.setPosition(Gdx.graphics.getWidth() / 2F - window.getWidth() / 2, Gdx.graphics.getHeight() / 2F - window.getHeight() / 2);
        closeBtn = new TextButton("Close", textButtonStyle);

        job1Description = new Label(GameOfLife.self.getCurrentJob().getBezeichnung(), uiSkin);
        //TODO Exception einfügen falls noch kein Job ausgewählt wurde

        window.add(job1Description).pad(10, 0, 0, 0).colspan(0).row();

        window.add(closeBtn).pad(150, 0, 0, 0).colspan(2);

        window.setScale(2F);

        closeBtn.addListener(new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // This is where we remove the window.
                window.remove();
            }
        });

        stage.addActor(window);
    }

    private void chooseJobWindow() {
        uiSkin = new Skin(Gdx.files.internal("uiskin.json"));
        jobSelection = JobData.getInstance();
        jobSelection.fillJobList();

        final Window window = new Window("", uiSkin);
        window.setSize(600, 450);
        window.setPosition(Gdx.graphics.getWidth() / 2F - window.getWidth() / 2, Gdx.graphics.getHeight() / 2F - window.getHeight() / 2);

        closeBtn = new TextButton("Close", textButtonStyle);
        job1Btn = new TextButton("Auswählen", textButtonStyle);
        job2Btn = new TextButton("Auswählen", textButtonStyle);

        jobs = new Job[2];
        jobSelection.mixCards();
        final Job[] jobs = jobSelection.getJobsToSelect(2);

        job1Description = new Label(jobs[0].getBezeichnung() + "\n" + jobs[0].getGehaltsListe().toString(), uiSkin);
        job2Description = new Label(jobs[1].getBezeichnung() + "\n" + jobs[1].getGehaltsListe().toString(), uiSkin);

        window.add(job1Description).pad(10, 0, 0, 0).colspan(1);
        window.add(job2Description).pad(10, 50, 0, 0).colspan(0).row();
        window.add(job1Btn).pad(0, 0, 0, 0).colspan(1);
        window.add(job2Btn).pad(0, 50, 0, 0).row();
        window.add(closeBtn).pad(150, 0, 0, 0).colspan(2);

        window.setScale(2F);

        job1Btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameOfLife.self.setCurrentJob(jobs[0]);
                window.remove();
                Gdx.app.log("JobSelection", "Job 1 chosen");
            }

            ;

        });

        job2Btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameOfLife.self.setCurrentJob(jobs[1]);
                window.remove();
                Gdx.app.log("JobSelection", "Job 2 chosen");

            }

            ;
        });

        closeBtn.addListener(new ChangeListener() {
            // This method is called whenever the actor is clicked. We override its behavior here.
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // This is where we remove the window.
                window.remove();
            }
        });

        stage.addActor(window);
    }

    /**
     *
     */
    private void createPlayerHUD() {
        lbUsernameAge = new Label("Username, Age", labelStyle);
        lbMoney = new Label("Money", labelStyle);
        lbLifepoints = new Label("Lifepoints", labelStyle);

        lbUsernameAge.setPosition(10, screenHeight - lbUsernameAge.getHeight() - 10);
        lbMoney.setPosition(10, screenHeight - lbUsernameAge.getHeight() - lbMoney.getHeight() - 20);
        lbLifepoints.setPosition(10, screenHeight - lbUsernameAge.getHeight() - lbMoney.getHeight() - lbLifepoints.getHeight() - 30);

        stage.addActor(lbUsernameAge);
        stage.addActor(lbMoney);
        stage.addActor(lbLifepoints);
    }

    /**
     *
     */
    private void refreshPlayerHUD() {
        final float time = 0.5f; // in seconds
        final Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {
            @Override
            public void run() {
                /*for (Player p : GameOfLife.players) {
                    if (p.hasTurn()) {
                        fillPlayerHUD(p);
                        break;
                    }
                }*/
                fillPlayerHUD(GameOfLife.self);
            }
        }, 0, time);
    }

    /**
     * @param p
     */
    private void fillPlayerHUD(Player p) {
        lbUsernameAge.setText(p.getUsername() + ", " + p.getAge());
        lbMoney.setText("Money: " + p.getMoney());
        lbLifepoints.setText("Lifepoints: " + p.getLifepoints());
    }


    /**
     *
     */
    private void createEventPopup() {
        Window.WindowStyle windowStyle = new Window.WindowStyle(standardFont, Color.WHITE, new TextureRegionDrawable(new TextureRegion(lightGrayTexture)));
        eventDialog = new Dialog("", windowStyle);
        eventDialog.button(new TextButton("Bestätigen", textButtonStyle));
        stage.addActor(eventDialog);
        hideEventPopup();
        //GameOfLife.client.sendPlayerTCP(GameOfLife.self);
    }

    /**
     * @param eventText
     */
    private void showEventPopUp(String eventText) {
        createEventPopup();
        eventDialog.text(eventText, labelStyle);
        eventDialog.show(stage);
        //GameOfLife.client.sendPlayerTCP(GameOfLife.self);
    }

    /**
     *
     */
    private void hideEventPopup() {
        eventDialog.hide();
        GameOfLife.client.sendPlayerTCP(GameOfLife.self);
    }

    @Override
    public void update(String payload) {

    }
}