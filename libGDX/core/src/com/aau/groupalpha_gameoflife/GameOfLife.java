package com.aau.groupalpha_gameoflife;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public final class GameOfLife extends Game {
	//Making a Singleton, so that we can access it from the screens
	public static GameOfLife INSTANCE;

	private GameOfLife(){
	}

	public static GameOfLife getInstance(){
		if(INSTANCE == null){
			INSTANCE = new GameOfLife();
		}

		return INSTANCE;
	}


	@Override
	public void create () {
		setScreen(new MainMenuScreen());
	}

}
