package com.aau.groupalpha_gameoflife;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainMenuScreen extends ScreenAdapter {
    public int screenWidth, screenHeight, centerWidth, centerHeight;
    public int buttonWidth, buttonHeight;

    public Vector2 buttonPosition;

    Stage stage;
    TextButton btnStartGame, btnJoinGame;
    TextButtonStyle textButtonStyle;
    BitmapFont font;
    Skin skin;

    public MainMenuScreen(){
        screenWidth = Gdx.graphics.getWidth();
        centerWidth = screenWidth/2;
        screenHeight = Gdx.graphics.getHeight();
        centerHeight = screenHeight/2;

        buttonWidth = screenWidth/5;
        buttonHeight = screenHeight/8;

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont();
        skin = new Skin();

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.LIGHT_GRAY);
        pixmap.fill();

        // Create a texture from the pixmap
        Texture lightGrayTexture = new Texture(pixmap);
        pixmap.setColor(Color.GRAY);
        pixmap.fill();
        Texture grayTextrue = new Texture(pixmap);
        // Dispose the pixmap since it's no longer needed
        pixmap.dispose();

        textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(grayTextrue)); // Set the up state texture
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(lightGrayTexture)); // Set the down state texture
        textButtonStyle.font = new BitmapFont(); // Set the font
        textButtonStyle.fontColor = Color.WHITE; // Set the font color
        textButtonStyle.font.getData().setScale(24 / font.getCapHeight());
        btnStartGame = new TextButton("Start Game", textButtonStyle); // Create the text button with the text and style
        btnJoinGame = new TextButton("Join Game", textButtonStyle); // Create the text button with the text and style
        buttonPosition = new Vector2(centerWidth-(buttonWidth/2), centerHeight-(buttonHeight/2));
        btnStartGame.setPosition(buttonPosition.x, buttonPosition.y ); // Set the position of the button
        btnStartGame.setSize(buttonWidth, buttonHeight); // Set the size of the button

        stage.addActor(btnStartGame); // Add the button to the stage

        stage.act(Gdx.graphics.getDeltaTime()); // Update the stage
        stage.draw(); // Draw the stage
    }

    @Override
    public void render(float delta){
        ScreenUtils.clear(0, 0, 0, 1);
        stage.draw();
    }

    public void dispose(){

    }

    @Override
    public void hide(){
        this.dispose();
    }
}
