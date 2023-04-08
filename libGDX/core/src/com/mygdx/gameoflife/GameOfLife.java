package com.mygdx.gameoflife;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class GameOfLife extends Game {
	private static GameOfLife INSTANCE;

	public GameOfLife(){
	}

	public static GameOfLife getInstance(){
		if(INSTANCE == null){
			INSTANCE = new GameOfLife();
		}

		return INSTANCE;
	}

	public static void changeScreen(Screen screen){
		INSTANCE.setScreen(screen);
	}

	@Override
	public void create() {
		setScreen(new MainMenuScreen());
	}
}
