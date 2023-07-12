package aau.se2.glock.alpha.gameoflife.screens;

import static aau.se2.glock.alpha.gameoflife.core.utilities.PlayerColor.BLUE;
import static aau.se2.glock.alpha.gameoflife.core.utilities.PlayerColor.GREEN;
import static aau.se2.glock.alpha.gameoflife.core.utilities.PlayerColor.PURPLE;
import static aau.se2.glock.alpha.gameoflife.core.utilities.PlayerColor.YELLOW;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
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
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.ArrayList;
import java.util.List;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.Board;
import aau.se2.glock.alpha.gameoflife.core.GameField;
import aau.se2.glock.alpha.gameoflife.core.Player;
import aau.se2.glock.alpha.gameoflife.core.jobs.Job;
import aau.se2.glock.alpha.gameoflife.core.jobs.JobData;
import aau.se2.glock.alpha.gameoflife.core.logic.Event;
import aau.se2.glock.alpha.gameoflife.core.logic.SpecialEvent;
import aau.se2.glock.alpha.gameoflife.core.special.Building;
import aau.se2.glock.alpha.gameoflife.core.special.BuildingType;
import aau.se2.glock.alpha.gameoflife.core.special.Car;
import aau.se2.glock.alpha.gameoflife.core.special.CarType;
import aau.se2.glock.alpha.gameoflife.core.special.SpecialData;
import aau.se2.glock.alpha.gameoflife.core.utilities.PlayerColor;
import aau.se2.glock.alpha.gameoflife.core.utilities.ProximityListener;
import aau.se2.glock.alpha.gameoflife.networking.packages.ServerInformation;
import aau.se2.glock.alpha.gameoflife.networking.server.ServerClass;
import space.earlygrey.shapedrawer.ShapeDrawer;

/**
 *
 */
public class GameScreen extends BasicScreen implements ProximityListener {

    private final float maxSpinDuration = 2f;
    private ClickListener btnQuitListener;
    private TextButton optionCButton;
    private TextButton btnConfirm;
    private TextButton.TextButtonStyle textButtonStyle;
    private TextButton btnJob;
    private Skin uiSkin;
    private Texture background;
    private Texture skateboard;
    private Texture skateBoardBlue;
    private Texture skateBoardPurple;
    private Texture skateBoardGreen;
    private Texture skateBoardYellow;
    private Button closeBtn;
    private Button optionAButton;
    private Button optionBButton;
    private Label job1Description;
    private Label optionTextA;
    private Label optionTextB;
    private Label specialEventText;
    private Group nextFieldButtonGroup; // Create a Group to hold actors
    private Group cheatingButtonGroup;
    private Group spinTheWheelGroup;
    private Group playersGroup;
    private Group reportWindowGroup;
    private Group winningWindowGroup;
    private ImageButton wheelImageButton;
    private ImageButton arrowImageButton;
    private Label lbUsernameAge;
    private Label lbMoney;
    private Label lbLifepoints;
    private Label lbPlayersOverview;
    private Label optionTextC;
    private Label actionLog1;
    private Label actionLog2;
    private Label actionLog3;
    private Label actionLog4;
    private List<Label> lbJoinedPlayers;
    private Label.LabelStyle labelStyle;
    private Dialog eventDialog;
    private boolean isSpinning = false;
    private float spinSpeed = 360f;
    private float spinDuration = 0f;
    private float spinAngle = 0f;
    private ShapeRenderer shapeRenderer;
    private Image arrowImage;
    private JobData jobSelection;
    private Window specialWindow;
    private SpecialEvent currentSpecialEvent;
    private boolean jobChosen = false;
    private int[]startNumbers;
    private ShapeDrawer drawer;
    private Vector2 oldPosition = new Vector2(0,0);
    private Vector2 newPosition = new Vector2(0,0);

    public GameScreen() {
        jobSelection = JobData.getInstance();
        jobSelection.fillJobList();
        GameOfLife.getProximitySensorInterface().setProximityListener(this);

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
        //createJobButton();
        createReportButton();
        chooseJobWindow();
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
            spinTheWheelGroup.clearChildren();
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
            ImageButton playerButton;
            PlayerColor playerColor = player.getColor();
            if (playerColor == BLUE)
                playerButton = new ImageButton(new TextureRegionDrawable(skateBoardBlue));
            else if (playerColor == PURPLE)
                playerButton = new ImageButton(new TextureRegionDrawable(skateBoardPurple));
            else if (playerColor == GREEN)
                playerButton = new ImageButton(new TextureRegionDrawable(skateBoardGreen));
            else if (playerColor == YELLOW)
                playerButton = new ImageButton(new TextureRegionDrawable(skateBoardYellow));
            else
                playerButton = new ImageButton(new TextureRegionDrawable(skateboard));


            ClickListener playerClickListener = new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.log("Button Clicked", "Button was clicked!");
                }
            };
            playerButton.addListener(playerClickListener);

            playerButton.setPosition(v3.x, v3.y);
            playerButton.setSize(100, 100);
            playersGroup.addActor(playerButton);
        }

        if (GameOfLife.self.hasTurn()) {
            if (!cheatingButtonGroup.hasChildren()) {
                createSpinTheWheelButton();
                spinTheWheelGroup.addActor(wheelImageButton);
                spinTheWheelGroup.addActor(arrowImageButton);
            } else {
                spinTheWheelGroup.clearChildren();
            }

            if (isSpinning) {
                //216 is starting point
                float arrowRotation = 216f;
                arrowImage.setRotation(((float) spinAngle + arrowRotation));
            }


            if (isSpinning) {
                spinTheWheel(delta);
            }
        } else {
            spinTheWheelGroup.clearChildren();
        }

        movementDrawing();

        stage.getBatch().end();
        stage.act(Gdx.graphics.getDeltaTime()); // Update the stage
        stage.draw(); // Draw the stage
    }

    private void movementDrawing(){
        drawer.setDefaultLineWidth(2f);
        drawer.setColor(1,0,0,1);
        drawer.line(oldPosition.x,oldPosition.y,newPosition.x,newPosition.y);
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
        super.initStage();

        stage.getBatch().setProjectionMatrix(gameCamera.combined);

        nextFieldButtonGroup = new Group();
        cheatingButtonGroup = new Group();
        spinTheWheelGroup = new Group();
        playersGroup = new Group();
        reportWindowGroup = new Group();
        winningWindowGroup = new Group();
        shapeRenderer = new ShapeRenderer();
        stage.addActor(nextFieldButtonGroup);
        stage.addActor(cheatingButtonGroup);
        stage.addActor(spinTheWheelGroup);
        stage.addActor(playersGroup);
        stage.addActor(reportWindowGroup);
        stage.addActor(winningWindowGroup);
    }

    /**
     * Initializes the textures used for UI elements.
     */
    protected void initTextures() {
        super.initTextures();

        background = new Texture(Gdx.files.internal("board.png"));
        skateboard = new Texture(Gdx.files.internal("skateboard.png"));
        skateBoardBlue = new Texture(Gdx.files.internal("skateboard_blue.png"));
        skateBoardPurple = new Texture(Gdx.files.internal("skateboard_purple.png"));
        skateBoardGreen = new Texture(Gdx.files.internal("skateboard_green.png"));
        skateBoardYellow = new Texture(Gdx.files.internal("skateboard_yellow.png"));


        Texture wheelTexture = new Texture("wheel.png");
        Texture arrowTexture = new Texture("arrow.png");

        arrowImage = new Image(arrowTexture);

        Drawable wheelDrawable = new TextureRegionDrawable(new TextureRegion(wheelTexture));
        wheelImageButton = new ImageButton(wheelDrawable);
        arrowImageButton = new ImageButton(new TextureRegionDrawable(arrowTexture));
    }

    /**
     * Create Button for rolling the dice.
     */
    private void createSpinTheWheelButton() {
        Vector3 v3Wheel = new Vector3(-35, -35, 0);
        Vector3 v3Arrow = new Vector3(-15, -35, 0);
        Vector3 v3Center = new Vector3(5, 4, 0);
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
                    /*
                     This method will be called when the TextButton is clicked
                    Player player = GameOfLife.self;
                    */

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

        wheelImageButton.addListener(btnRollDiceListener);
        arrowImageButton.addListener(btnRollDiceListener);
    }


    // !!! JAVA DOC @param BESCHREIBEN !!!

    /**
     * Create Button for rolling the dice.
     */
    private void createMenuCheating() {
        //Create a Start Game Button
        TextButton btnCheat1Field = new TextButton("+1 Feld", textButtonStyle); // Create the text button with the text and style
        btnCheat1Field.setPosition(buttonPosition.x, (float) (buttonPosition.y + (buttonHeight * 1.25))); // Set the position of the button
        btnCheat1Field.setSize(buttonWidth, buttonHeight); // Set the size of the button

        cheatingButtonGroup.addActor(btnCheat1Field);

        //Create a Start Game Button
        TextButton btnCheat2Fields = new TextButton("+2 Felder", textButtonStyle); // Create the text button with the text and style
        btnCheat2Fields.setPosition(buttonPosition.x, (float) (buttonPosition.y + (buttonHeight * 1.25 * 2))); // Set the position of the button
        btnCheat2Fields.setSize(buttonWidth, buttonHeight); // Set the size of the button

        cheatingButtonGroup.addActor(btnCheat2Fields);

        //Create a Start Game Button
        TextButton btnCheat3Fields = new TextButton("+3 Felder", textButtonStyle); // Create the text button with the text and style
        btnCheat3Fields.setPosition(buttonPosition.x, (float) (buttonPosition.y + (buttonHeight * 1.25 * 3))); // Set the position of the button
        btnCheat3Fields.setSize(buttonWidth, buttonHeight); // Set the size of the button

        cheatingButtonGroup.addActor(btnCheat3Fields);

        // Create a ClickListener
        ClickListener btnCheat1FieldListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                GameOfLife.self.cheat(1);

                cheatingButtonGroup.clearChildren();
            }

        };
        btnCheat1Field.addListener(btnCheat1FieldListener);

        ClickListener btnCheat2FieldsListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                GameOfLife.self.cheat(2);

                cheatingButtonGroup.clearChildren();
            }

        };
        btnCheat2Fields.addListener(btnCheat2FieldsListener);

        ClickListener btnCheat3FieldsListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

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
        Button nextFieldButton1 = new TextButton("Hier", textButtonStyle);
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
        Button nextFieldButton2 = new TextButton("Hier", textButtonStyle);
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
        if (!GameOfLife.self.makeMove()) {
            GameField gameField = Board.getInstance().getGameFields().get(GameOfLife.self.getPosition());
            chooseNextStep(gameField);
        }

        if (GameOfLife.self.getMoveCount() == 0) {
            handleEvent(GameOfLife.self);
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
            if (!GameOfLife.self.makeMove()) {
                GameField gameField = Board.getInstance().getGameFields().get(GameOfLife.self.getPosition());

                chooseNextStep(gameField);
            } else {
                if (GameOfLife.self.getMoveCount() == 0) {
                    Gdx.app.log("Zeile", "673");
                    handleEvent(GameOfLife.self);
                }
            }
        }
    }

    /**
     *
     */
    private void createQuitButton() {
        //Create a Back Button
        TextButton btnQuit = new TextButton("quit", textButtonStyle); // Create the text button with the text and style
        btnQuit.setSize((buttonWidth * 5) / 7f, buttonHeight); // Set the size of the button
        btnQuit.setPosition(30, 30); // Set the position of the button

        stage.addActor(btnQuit); // Add the button to the stage

        // Create a ClickListener
        btnQuitListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                new Thread(() -> {
                    if (GameOfLife.self.isHost()) {
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
        btnJob = new TextButton("Current Job", textButtonStyle);
        btnJob.setSize((buttonWidth * 5) / 7f, buttonHeight);
        btnJob.setPosition(Gdx.graphics.getWidth() - (buttonWidth * 5) / 7f - 30f, Gdx.graphics.getHeight() - buttonHeight - 30f);

        stage.addActor(btnJob);

        ClickListener btnJobListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("ShowsCurrentJob", "Works");
                if (jobChosen) {
                    showCurrentJob();
                }
            }
        };

        btnJob.addListener(btnJobListener);
    }



    /**
     *
     */
    private void createReportButton() {
        btnJob = new TextButton("Report", textButtonStyle);
        btnJob.setSize((buttonWidth * 5) / 7f, buttonHeight);
        btnJob.setPosition(Gdx.graphics.getWidth() - (buttonWidth * 5) / 7f - 30f, 30f);
        stage.addActor(btnJob);

        ClickListener btnJobListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                reportPlayerWindow();
            }
        };

        btnJob.addListener(btnJobListener);
    }

    private void showCurrentJob() {
        //loads uiSkin from files
        uiSkin = new Skin(Gdx.files.internal(GameOfLife.FILE_UISKIN_JONS));

        final Window window = new Window("", uiSkin);
        window.setSize(600, 450);
        window.setPosition(Gdx.graphics.getWidth() / 2F - window.getWidth() / 2, Gdx.graphics.getHeight() / 2F - window.getHeight() / 2);
        closeBtn = new TextButton("Close", textButtonStyle);

        job1Description = new Label(GameOfLife.self.getCurrentJob().getBezeichnung(), uiSkin);

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

    public void chooseJobWindow() {
        uiSkin = new Skin(Gdx.files.internal(GameOfLife.FILE_UISKIN_JONS));
        jobSelection = JobData.getInstance();
        jobSelection.fillJobList();

        final Window window = new Window("", uiSkin);
        window.setSize(600, 450);
        window.setPosition(Gdx.graphics.getWidth() / 2F - window.getWidth() / 2, Gdx.graphics.getHeight() / 2F - window.getHeight() / 2);

        closeBtn = new TextButton("Close", textButtonStyle);
        Button job1Btn = new TextButton("Auswählen", textButtonStyle);
        Button job2Btn = new TextButton("Auswählen", textButtonStyle);

        jobSelection.mixCards();
        final Job[] jobs = jobSelection.getJobsToSelect(2);

        //reads the individual salaries of all levels in the first job and concats them
        String job1Text = jobs[0].getGehaltsListe().get(0).toString();
        for (int i = 1; i < jobs[0].getGehaltsListe().size(); i++) {
            job1Text = job1Text.concat("\n").concat(jobs[0].getGehaltsListe().get(i).toString());
        }

        //reads the individual salaries of all levels in the second job and concats them
        String job2Text = jobs[1].getGehaltsListe().get(0).toString();
        for (int i = 1; i < jobs[1].getGehaltsListe().size(); i++) {
            job2Text = job2Text.concat("\n").concat(jobs[1].getGehaltsListe().get(i).toString());
        }

        job1Description = new Label(jobs[0].getBezeichnung() + "\n" + job1Text, uiSkin);
        Label job2Description = new Label(jobs[1].getBezeichnung() + "\n" + job2Text, uiSkin);

        window.add(job1Description).pad(10, 0, 0, 0).colspan(1);
        window.add(job2Description).pad(10, 50, 0, 0).colspan(0).row();
        window.add(job1Btn).pad(0, 0, 0, 0).colspan(1);
        window.add(job2Btn).pad(0, 50, 0, 0).row();

        window.setScale(2F);

        job1Btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                initJobBtnListenerContent(jobs, 0, window);
            }
        });

        job2Btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                initJobBtnListenerContent(jobs, 1, window);
            }
        });
        stage.addActor(window);
    }

    private void initJobBtnListenerContent(Job[] jobs, int jobsIndex, Window window) {
        GameOfLife.self.setCurrentJob(jobs[jobsIndex]);
        Gdx.app.log("JobSelection", "Job " + jobsIndex + 1 + " chosen");
        jobChosen = true;
        window.remove();
        if (GameOfLife.self.getAge() > 18) {
            specialWindow.remove();
            showRoundSummary();
        }
        GameOfLife.client.sendPlayerTCP(GameOfLife.self);
    }

    private void reportPlayerWindow() {
        uiSkin = new Skin(Gdx.files.internal(GameOfLife.FILE_UISKIN_JONS));
        jobSelection = JobData.getInstance();
        jobSelection.fillJobList();

        final Window window = new Window("", uiSkin);
        window.setSize(600, 450);
        window.setPosition(Gdx.graphics.getWidth() / 2f - window.getWidth() / 2, Gdx.graphics.getHeight() / 2f - window.getHeight() / 2);

        for (Player player : GameOfLife.players) {
            if (player == GameOfLife.self) {
                continue;
            }

            String username = player.getUsername();
            Label usernameLabel = new Label(username, labelStyle);
            TextButton playerButton = new TextButton("Report", textButtonStyle);
            playerButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    GameOfLife.client.sendReportPlayerTCP(player);
                    reportWindowGroup.clearChildren();
                }
            });

            window.add(usernameLabel).padTop(10);
            window.add(playerButton).padLeft(10).padTop(10).row();
        }

        closeBtn = new TextButton("Close", textButtonStyle);
        closeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                reportWindowGroup.clearChildren();
            }
        });
        window.add(closeBtn).padTop(10).colspan(2).row();

        reportWindowGroup.addActor(window);
    }

    private void showWinningWindow() {
        uiSkin = new Skin(Gdx.files.internal(GameOfLife.FILE_UISKIN_JONS));

        Window winningWindow = new Window("", uiSkin);
        winningWindow.setSize(600, 450);
        winningWindow.setPosition(Gdx.graphics.getWidth() / 2f - winningWindow.getWidth() / 2, Gdx.graphics.getHeight() / 2f - winningWindow.getHeight() / 2);

        for (Player player : GameOfLife.players) {
            player.setLifepoints(player.getLifepoints() + (player.getMoney() / 1000));
            player.setMoney(0);
        }

        GameOfLife.players.sort((o1, o2) -> o2.getLifepoints() - o1.getLifepoints());
        Label winningWindowHeading = new Label("Resultat", labelStyle);
        winningWindow.add(winningWindowHeading).row();

        int place = 1;
        for (Player player : GameOfLife.players) {
            String username = player.getUsername();
            Label usernameLabel = new Label(place + ". " + username, labelStyle);
            Label lifepointsLabel = new Label(player.getLifepoints() + "", labelStyle);

            winningWindow.add(usernameLabel).padTop(10);
            winningWindow.add(lifepointsLabel).padLeft(10).padTop(10).row();
            place++;
        }

        closeBtn = new TextButton("Close", textButtonStyle);
        closeBtn.addListener(btnQuitListener);
        winningWindow.add(closeBtn).padTop(10).colspan(2).row();

        winningWindowGroup.addActor(winningWindow);
    }


    /**
     *
     */
    private void createPlayerHUD() {
        startNumbers = new int[]{0,3,6,9};
        lbUsernameAge = new Label("Username, Age", labelStyle);
        lbMoney = new Label("Money", labelStyle);
        lbLifepoints = new Label("Lifepoints", labelStyle);

        lbPlayersOverview = new Label("Players (" + GameOfLife.players.size() + "):", labelStyle);

        lbJoinedPlayers = new ArrayList<>();
        fillJoinedPlayers();

        lbUsernameAge.setPosition(10, screenHeight - lbUsernameAge.getHeight() - 10);
        lbMoney.setPosition(10, lbUsernameAge.getY() - lbMoney.getHeight() - 10);
        lbLifepoints.setPosition(10, lbMoney.getY() - lbLifepoints.getHeight() - 10);

        lbPlayersOverview.setPosition(10, lbLifepoints.getY() - lbPlayersOverview.getHeight() - 200);
        actionLog1 = new Label("", labelStyle);
        actionLog2 = new Label("", labelStyle);
        actionLog3 = new Label("", labelStyle);
        actionLog4 = new Label("", labelStyle);

        actionLog1.setPosition(Gdx.graphics.getWidth() - (350),Gdx.graphics.getHeight() - 60);
        actionLog2.setPosition(actionLog1.getX(),actionLog1.getY() - actionLog2.getHeight() - 10);
        actionLog3.setPosition(actionLog2.getX(),actionLog2.getY() - actionLog3.getHeight() - 10);
        actionLog4.setPosition(actionLog3.getX(),actionLog3.getY() - actionLog4.getHeight() - 10);

        stage.addActor(lbUsernameAge);
        stage.addActor(lbMoney);
        stage.addActor(lbLifepoints);
        stage.addActor(lbPlayersOverview);
        stage.addActor(actionLog1);
        stage.addActor(actionLog2);
        stage.addActor(actionLog3);
        stage.addActor(actionLog4);

        Pixmap pixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.drawPixel(0,0);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        drawer = new ShapeDrawer(stage.getBatch(),new TextureRegion(texture,0,0,1,1));

    }

    private void fillJoinedPlayers() {
        for (Label l : lbJoinedPlayers) {
            l.remove();
        }
        lbJoinedPlayers.clear();

        StringBuilder b = new StringBuilder();

        for (int i = 1; i <= GameOfLife.players.size(); i++) {
            Player p = GameOfLife.players.get(i - 1);
            b.append(p.getUsername()).append(", ").append(p.getAge());
            if (p.getCurrentJob() != null) {
                b.append(", ").append(p.getCurrentJob().getBezeichnung());
            }
            if (p.hasTurn()) {
                b.append(" (turn)");
            } else if (!p.isOnline()) {
                b.append(" (offline)");
            }
            Label l = new Label(b.toString(), labelStyle);
            l.setPosition(20, lbPlayersOverview.getY() - i * l.getHeight() - 10);
            stage.addActor(l);
            lbJoinedPlayers.add(l);
            b.clear();
        }
    }

    //ActionLog for all Players
    private void playerLog(){
        ArrayList<Label> logs = new ArrayList<>();
        logs.add(actionLog1);
        logs.add(actionLog2);
        logs.add(actionLog3);
        logs.add(actionLog4);

        for (int i = 0; i < GameOfLife.players.size(); i++){
            Player p = GameOfLife.players.get(i);
            int oldNumber = startNumbers[i];
            if(oldNumber != p.getPosition()){
                logs.get(i).setText(p.getUsername()+" -> "+ oldNumber +" -> "+p.getPosition());
                startNumbers[i] = p.getPosition();

                oldPosition = new Vector2(Board.getInstance().getGameFields().get(oldNumber).getPosition().x,Board.getInstance().getGameFields().get(oldNumber).getPosition().y);
                newPosition = new Vector2(Board.getInstance().getGameFields().get(p.getPosition()).getPosition().x,Board.getInstance().getGameFields().get(p.getPosition()).getPosition().y);

            }
        }


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
                if (GameOfLife.checkIfGameOver()) {
                    showWinningWindow();
                }
                fillJoinedPlayers();
                playerLog();
                fillPlayerHUD(GameOfLife.self);
            }
        }, 0, time);
    }

    /**
     *
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
    }

    /**
     * @param eventText
     */
    private void showEventPopUp(String eventText) {
        createEventPopup();
        eventDialog.text(eventText + "\n" + generateSummaryString(recieveWage()), labelStyle);
        eventDialog.show(stage);
    }

    /**
     *
     */
    private void hideEventPopup() {
        eventDialog.hide();
        GameOfLife.client.sendPlayerTCP(GameOfLife.self);
    }

    private void handleEvent(Player player) {
        Event event = player.getEvent();
        if (event instanceof SpecialEvent) {
            System.out.println("EVENT:" + ((SpecialEvent) event).getType());
            currentSpecialEvent = (SpecialEvent) event;
            showSpecialEventPopup();
        } else {
            player.changeBalance(event.getCash(), event.getLp());
            showEventPopUp(addLineBreak(event.getText()));
        }
    }

    private void showSpecialEventPopup() {
        createSpecialEventPopup();
    }

    private void createSpecialEventPopup() {
        uiSkin = new Skin(Gdx.files.internal(GameOfLife.FILE_UISKIN_JONS));

        specialWindow = new Window("", uiSkin);
        specialWindow.setSize(600, 450);
        specialWindow.setPosition(Gdx.graphics.getWidth() / 2F - specialWindow.getWidth() / 2, Gdx.graphics.getHeight() / 2F - specialWindow.getHeight() / 2);

        optionAButton = new TextButton("OptionA", textButtonStyle);
        optionBButton = new TextButton("OptionB", textButtonStyle);
        btnConfirm = new TextButton("Bestätigen", textButtonStyle);

        optionTextA = new Label(currentSpecialEvent.getOptionA(), uiSkin);
        optionTextB = new Label(currentSpecialEvent.getOptionB(), uiSkin);
        specialEventText = new Label(currentSpecialEvent.getMessage(), uiSkin);

        specialWindow.add(optionTextA).pad(10, 0, 0, 0).colspan(1);
        specialWindow.add(optionTextB).pad(10, 50, 0, 0).colspan(0).row();
        specialWindow.add(optionAButton).pad(0, 0, 0, 0).colspan(1);
        specialWindow.add(optionBButton).pad(0, 50, 0, 0).row();
        specialWindow.add(specialEventText).pad(30, 0, 0, 0);
        specialWindow.setScale(2F);

        optionAButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String evtReturnText = currentSpecialEvent.eventOptionA();
                checkforEventType(evtReturnText);
                Gdx.app.log("SpecialEvent", "Option A chosen");
            }
        });

        optionBButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String evtReturnText = currentSpecialEvent.eventOptionB();
                checkforEventType(evtReturnText);
                Gdx.app.log("SpecialEvent", "Option B chosen");
            }

            ;
        });

        stage.addActor(specialWindow);
    }

    private void openCarShop() {
        SpecialData specialData = new SpecialData();
        Car car1 = specialData.selectCar(CarType.HATCHBACK);
        Car car2 = specialData.selectCar(CarType.SPORTSCAR);
        optionTextA.setText(car1.toString());
        optionTextB.setText(car2.toString());
        specialEventText.setText("Such dir ein Auto aus");
        optionAButton.clearListeners();
        optionAButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameOfLife.self.addCar(car1);
                Gdx.app.log("Car Shop:", "Car1 Selected");
                specialWindow.remove();
                showRoundSummary();
            }
        });
        optionBButton.clearListeners();
        optionBButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameOfLife.self.addCar(car2);
                Gdx.app.log("Car Shop:", "Car2 Selected");
                specialWindow.remove();
                showRoundSummary();
            }
        });
    }

    private void openHouseShop() {
        SpecialData specialData = new SpecialData();
        Building house1 = specialData.selectHouse(BuildingType.SINGLEHOUSE);
        Building house2 = specialData.selectHouse(BuildingType.FAMILYHOUSE);
        Building house3 = specialData.selectHouse(BuildingType.VILLA);
        optionCButton = new TextButton("OptionC", textButtonStyle);
        optionTextC = new Label(house3.toString(), uiSkin);
        optionTextA.setText(house1.toString());
        optionTextB.setText(house2.toString());
        rearrangeButtonsForHouseShop();
        optionAButton.clearListeners();
        optionBButton.clearListeners();
        optionCButton.clearListeners();
        optionAButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameOfLife.self.changeBalance(-house1.getPrice(), 0);
                GameOfLife.self.addBuilding(house1);
                Gdx.app.log("Building:", "Singlehouse bought");
                specialWindow.remove();
                showRoundSummary();
            }
        });
        optionBButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameOfLife.self.changeBalance(-house2.getPrice(), 0);
                GameOfLife.self.addBuilding(house2);
                Gdx.app.log("Car Shop:", "FamilyHouse bought");
                specialWindow.remove();
                showRoundSummary();
            }
        });
        optionCButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameOfLife.self.changeBalance(-house3.getPrice(), 0);
                GameOfLife.self.addBuilding(house3);
                Gdx.app.log("Car Shop:", "Villa bought");
                specialWindow.remove();
                showRoundSummary();
            }
        });
        Gdx.app.log("House Shop:", "open");
    }

    private void rearrangeButtonsForHouseShop() {
        specialWindow.clearChildren();
        specialWindow.add(optionTextA).colspan(1);
        specialWindow.add(optionTextB).colspan(1);
        specialWindow.add(optionTextC).colspan(1).row();
        specialWindow.add(optionAButton).colspan(1);
        specialWindow.add(optionBButton).colspan(1);
        specialWindow.add(optionCButton).colspan(1).row();
    }

    private String addLineBreak(String input) {
        java.lang.StringBuilder result = new java.lang.StringBuilder();
        String[] split = input.split("\\. ");
        for (int i = 0; i < split.length - 1; i++) {
            result.append(split[i]).append("\n");
        }
        result.append(split[split.length - 1]);
        return result.toString();
    }

    void checkforEventType(String evtReturnText) {
        String buyHouseCond = "buyHouse";
        String buyCarCond = "buyCar";
        String changeCareerCond = "changeCareer";
        if (evtReturnText.equals(buyCarCond)) {
            openCarShop();
        } else if (evtReturnText.equals(buyHouseCond)) {
            openHouseShop();
        } else if (evtReturnText.equals(changeCareerCond)) {
            specialWindow.remove();
            chooseJobWindow();
        } else openConfirmWindow(evtReturnText);
    }

    void openConfirmWindow(String evtReturnText) {
        specialWindow.removeActor(optionAButton);
        specialWindow.removeActor(optionBButton);
        specialWindow.removeActor(optionTextA);
        specialWindow.removeActor(optionTextB);
        specialWindow.removeActor(specialEventText);
        specialWindow.add(specialEventText).row();
        specialEventText.setText(evtReturnText);
        specialWindow.add(btnConfirm);
        btnConfirm.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                specialWindow.remove();
                showRoundSummary();
            }
        });
    }

    @Override
    public void update(String payload) {
    }

    private void showRoundSummary() {
        int wage = recieveWage();
        String summary = generateSummaryString(wage);
        createEventPopup();
        eventDialog.text(summary, labelStyle);
        eventDialog.show(stage);
        GameOfLife.client.sendPlayerTCP(GameOfLife.self);
    }

    String generateSummaryString(int wage) {
        String summary = "Rundenende:\n";
        summary += "Du erhälst dein Gehalt: " + wage + " €";
        summary += recieveBuildingPayout();
        summary += recieveCarLP();
        return summary;
    }

    private int recieveWage() {
        Job job = GameOfLife.self.getCurrentJob();
        int wage = job.getGehaltsListe().get(job.getGehaltsStufe());
        GameOfLife.self.changeBalance(wage, 0);
        return wage;
    }

    private String recieveBuildingPayout() {
        java.lang.StringBuilder result = new java.lang.StringBuilder();
        List<Building> buildingList = GameOfLife.self.getBuildingList();
        for (Building building : buildingList) {
            int payout = building.getPrice() / 10;
            GameOfLife.self.changeBalance(payout, 0);
            result.append("\nDu erhälst durch ").append(building.getType()).append(" ").append(payout).append("€");
        }
        return result.toString();
    }

    private String recieveCarLP() {
        java.lang.StringBuilder result = new java.lang.StringBuilder();
        List<Car> carList = GameOfLife.self.getCarList();
        for (Car car : carList) {
            int payout = car.getLp();
            GameOfLife.self.changeBalance(0, payout);
            result.append("\nDu erhälst durch ").append(car.getType()).append(" ").append(payout).append("LP");
        }
        return result.toString();
    }

}