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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.Board;
import aau.se2.glock.alpha.gameoflife.core.GameField;
import aau.se2.glock.alpha.gameoflife.core.Player;

public class GameScreen implements Screen {

    private OrthographicCamera gameCamera;
    private Viewport gameViewPort;
    private int screenWidth, screenHeight, centerWidth, centerHeight;
    private int buttonWidth, buttonHeight;

    public Vector2 buttonPosition;

    Stage stage;
    TextButton btnRollDice;
    TextButton.TextButtonStyle textButtonStyle;
    BitmapFont standardFont, bigFont;
    Skin skin;
    Texture background, skateboard;
    Button nextFieldButton1, nextFieldButton2;
    Group nextFieldButtonGroup; // Create a Group to hold actors
    Texture lightGrayTexture, grayTextrue;
    Texture wheelTexture;
    Texture arrowTexture;

    //Wheel
    int wheelSize = 100;
    boolean isSpinning = false;
    float arrowX = -25f;
    float arrowY = -25f;
    float arrowWidth = 50f;
    float arrowHeight = 50f;
    float arrowRotation = 216f; //216 is starting point
    float spinSpeed = 360f;
    float maxSpinDuration = 2f;
    float spinDuration = 0f;
    float spinAngle = 0f;
    int selectedSection = 0;
    boolean spinningEnded = true;

    public GameScreen() {
        gameCamera = new OrthographicCamera();
        gameViewPort = new StretchViewport(800, 400, gameCamera);

        initScreenDimensions();

        initFonts();

        initStage();

        initTextures();

        createButton();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        stage.getBatch().setProjectionMatrix(gameCamera.combined);

        stage.getBatch().begin();

        stage.getBatch().draw(background, -gameCamera.viewportWidth / 3, -gameCamera.viewportHeight / 2, gameCamera.viewportWidth / 3 * 2, gameCamera.viewportHeight);
        for (Player player : GameOfLife.players) {
            GameField currentField = Board.getInstance().getGameFields().get(player.getPosition());
            stage.getBatch().draw(skateboard, currentField.getPosition().x - 20, currentField.getPosition().y - 20, 40, 40);
        }

        if (GameOfLife.self.isHasTurn()) {
            stage.getBatch().draw(wheelTexture, -(wheelSize / 2), -(wheelSize / 2), (wheelSize), (wheelSize));
            stage.getBatch().draw(arrowTexture, arrowX, arrowY, arrowWidth / 2f, arrowHeight / 2f, arrowWidth, arrowHeight, 1f, 1f, spinAngle + arrowRotation, 0, 0, arrowTexture.getWidth(), arrowTexture.getHeight(), false, false);

            if (isSpinning) {
                spinTheWheel(delta);
            }
        }

        stage.getBatch().end();

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
        nextFieldButtonGroup = new Group();
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

        background = new Texture(Gdx.files.internal("board.png"));
        skateboard = new Texture(Gdx.files.internal("skateboard.png"));

        wheelTexture = new Texture("wheel.png");
        arrowTexture = new Texture("arrow.png");
    }

    /**
     * Create Button for rolling the dice.
     */
    private void createButton() {
        //create a textButtonStyle
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(grayTextrue)); // Set the up state texture
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(lightGrayTexture)); // Set the down state texture
        textButtonStyle.font = standardFont; // Set the font
        textButtonStyle.fontColor = Color.WHITE; // Set the font color

        //Create a Start Game Button
        btnRollDice = new TextButton("Würfeln", textButtonStyle); // Create the text button with the text and style
        btnRollDice.setPosition(buttonPosition.x, (float) (buttonPosition.y - (buttonHeight * 1.25))); // Set the position of the button
        btnRollDice.setSize(buttonWidth, buttonHeight); // Set the size of the button

        stage.addActor(btnRollDice); // Add the button to the stage

        // Create a ClickListener
        ClickListener btnRollDiceListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // This method will be called when the TextButton is clicked
                boolean isInTurn = true;

                Player player = GameOfLife.players.get(0);
                int moveCount = player.rollTheDice();
                Gdx.app.log("moveCount", moveCount + "");

                //calculating the angle that the arrow has to spin, so that it stops at the correct number
                float maxSpingAngle = (moveCount * 36) - 18;
                System.out.println(spinAngle);
                spinSpeed = ((maxSpingAngle) + 720) / maxSpinDuration;
                spinAngle = 0;

                isSpinning = true;
            }

        };
        btnRollDice.addListener(btnRollDiceListener);
    }

    /**
     * Chooses the next step for the player on the game field.
     *
     * @param gameField The current game field.
     */
    private void chooseNextStep(GameField gameField) {
        //Get the next game field based on player choice (1 or 2)
        GameField nextGameField1 = new Board().getGameFields().get(gameField.getIndexOfNextGameFields().get(0));

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
        GameField nextGameField2 = new Board().getGameFields().get(gameField.getIndexOfNextGameFields().get(1));

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

        // Add the next field buttons to the stage
        stage.addActor(nextFieldButtonGroup);
    }


    /**
     * When the player has chosen which step he wants to take next this function is called
     * we set the current position of the player to the chosen field and makeMove is called again
     *
     * @param index
     */
    private void stepChoosen(int index) {
        // Update player's choice and position
        Player player = GameOfLife.players.get(0);
        player.chooseDirection(index);
        GameOfLife.players.set(0, player);
        nextFieldButtonGroup.clearChildren();

        //Check if player can still move
        GameField gameField = Board.getInstance().getGameFields().get(player.getPosition());
        if (!player.makeMove()) {
            gameField = Board.getInstance().getGameFields().get(player.getPosition());
            GameOfLife.players.set(0, player);
            chooseNextStep(gameField);
        }

        GameOfLife.players.set(0, player);
    }

    private void spinTheWheel(float delta){
        // increase spin duration and angle
        spinDuration += delta;
        spinAngle -= spinSpeed * delta;

        // select a random section once spin duration exceeds maximum duration
        if (spinDuration > maxSpinDuration) {
            spinDuration = 0f;
            spinSpeed = 0f;
            selectedSection = MathUtils.random(10);
            isSpinning = false;

            Player player = GameOfLife.players.get(0);
            GameField gameField = Board.getInstance().getGameFields().get(player.getPosition());
            if (player.makeMove() == false) {
                gameField = Board.getInstance().getGameFields().get(player.getPosition());

                GameOfLife.players.set(0, player);
                chooseNextStep(gameField);
            }

            GameOfLife.players.set(0, player);
        }
    }
}
