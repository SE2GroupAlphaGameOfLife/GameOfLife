package com.mygdx.gameoflife;

import com.badlogic.gdx.Game;

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

	@Override
	public void create() {
		setScreen(new MainMenuScreen());
	}
}
