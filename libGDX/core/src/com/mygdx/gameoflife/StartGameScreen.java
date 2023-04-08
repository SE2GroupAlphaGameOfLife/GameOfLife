package com.mygdx.gameoflife;


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

public class StartGameScreen implements Screen {
    private OrthographicCamera gameCamera;
    private Viewport gameViewPort;
    private int screenWidth, screenHeight, centerWidth, centerHeight;
    private int buttonWidth, buttonHeight;

    public Vector2 buttonPosition;

    Stage stage;
    TextButton btnStartGame, btnJoinGame, btnSettings;
    TextButtonStyle textButtonStyle;
    BitmapFont standardFont, bigFont;
    Skin skin;

    public StartGameScreen(){
        gameCamera = new OrthographicCamera();
        gameViewPort = new StretchViewport(800,400, gameCamera);

        screenWidth = Gdx.graphics.getWidth();
        centerWidth = screenWidth/2;
        screenHeight = Gdx.graphics.getHeight();
        centerHeight = screenHeight/2;

        buttonWidth = screenWidth/5;
        buttonHeight = screenHeight/8;

        buttonPosition = new Vector2(centerWidth-(buttonWidth/2), centerHeight - buttonHeight);


        //Generate a standard and a big font
        //We have to use the FreeTypeFontGenerate because otherwise we have a fixed size font and scaling will not change resolution so it gets really low resolution
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Accuratist.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36; // set the desired font size
        standardFont = generator.generateFont(parameter);
        parameter.size = 128;
        bigFont = generator.generateFont(parameter);
        generator.dispose(); // dispose the generator after generating the font

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        skin = new Skin();


        //Create some Textures for our components
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);

        pixmap.setColor(Color.LIGHT_GRAY);
        pixmap.fill();
        Texture lightGrayTexture = new Texture(pixmap);

        pixmap.setColor(Color.GRAY);
        pixmap.fill();
        Texture grayTextrue = new Texture(pixmap);

        //dispose the pixmap after we finished using it
        pixmap.dispose();

        //Create Game of Life Title
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = bigFont; // Set the font for the label
        labelStyle.fontColor = Color.WHITE; // Set the font color for the label
        Label label = new Label("Game of Life", labelStyle); // Create the label with the text and style
        label.setPosition(centerWidth - (label.getWidth() / 2), centerHeight + (buttonHeight * 2)); // Set the position of the label
        stage.addActor(label); // Add the label to the stage

        // Create a TextFieldStyle
        final TextFieldStyle textFieldStyle = new TextFieldStyle();
        textFieldStyle.font = standardFont; // Set the font for the text field
        textFieldStyle.fontColor = Color.LIGHT_GRAY; // Set the font color for the text field
        textFieldStyle.cursor = new TextureRegionDrawable(new TextureRegion(new TextureRegion(lightGrayTexture))); // Set the cursor for the text field
        // Create a NinePatchDrawable with a border color
        final NinePatchDrawable borderDrawable = new NinePatchDrawable(new NinePatch(new Texture(Gdx.files.internal("border.png"))));
        // Set the border color
        borderDrawable.getPatch().setColor(Color.GRAY);
        // Set the background for the text field
        textFieldStyle.background = borderDrawable;
        //textFieldStyle.background = new TextureRegionDrawable(new TextureRegion(new TextureRegion(lightGrayTexture))); // Set the background for the text field

        // Create the text field using the registered style
        final TextField textField = new TextField("", textFieldStyle); // You can set an initial text value in the first parameter of the TextField constructor
        textField.setPosition(centerWidth - (buttonWidth / 2), (float)(buttonPosition.y + (buttonHeight*1.25))); // Set the position of the text field
        textField.setSize(buttonWidth, buttonHeight); // Set the size of the text field
        // Set the placeholder text
        textField.setMessageText("Enter username"); // Set the placeholder text

        textFieldStyle.background.setLeftWidth(screenWidth/50); // Set the left padding
        textFieldStyle.background.setRightWidth(screenWidth/50); // Set the right padding
        textFieldStyle.background.setTopHeight(screenWidth/50); // Set the top padding
        textFieldStyle.background.setBottomHeight(screenWidth/50); // Set the bottom padding

        stage.addActor(textField); // Add the text field to the stage

        //create a textButtonStyle
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(grayTextrue)); // Set the up state texture
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(lightGrayTexture)); // Set the down state texture
        textButtonStyle.font = standardFont; // Set the font
        textButtonStyle.fontColor = Color.WHITE; // Set the font color

        //Create a Start Game Button
        btnStartGame = new TextButton("Start Game", textButtonStyle); // Create the text button with the text and style
        btnStartGame.setPosition(buttonPosition.x, buttonPosition.y ); // Set the position of the button
        btnStartGame.setSize(buttonWidth, buttonHeight); // Set the size of the button

        //Create a Join Game Button
        btnJoinGame = new TextButton("Join Game", textButtonStyle); // Create the text button with the text and style
        btnJoinGame.setPosition(buttonPosition.x, (float)(buttonPosition.y - (buttonHeight*1.25)));
        btnJoinGame.setSize(buttonWidth, buttonHeight);

        stage.addActor(btnStartGame); // Add the button to the stage
        stage.addActor(btnJoinGame);

        // Add a ChangeListener to the TextField
        textField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Check if the length of the text is greater than 0
                if (textField.getText().length() > 0) {
                    // Set the font color to light gray
                    textField.setColor(Color.LIGHT_GRAY);
                    textField.getStyle().fontColor = Color.LIGHT_GRAY;
                } else {
                    
                }
            }
        });

        // Create a ClickListener
        ClickListener btnStartGameListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // This method will be called when the TextButton is clicked
                if(textField.getText().length() < 1){
                    textField.setColor(Color.RED);
                    textField.getStyle().messageFontColor = Color.RED;
                    Gdx.app.log("TextButton Clicked", "\"" +textField.getText()+"\" "+textField.getText().length());
                }

                Gdx.app.log("TextButton Clicked", "TextButton was clicked!");
            }
        };

        btnStartGame.addListener(btnStartGameListener);



    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta){
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

    public void dispose(){

    }

    @Override
    public void hide(){
        this.dispose();
    }
}