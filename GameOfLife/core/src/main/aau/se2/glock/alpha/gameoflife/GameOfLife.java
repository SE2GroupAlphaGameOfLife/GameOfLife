package aau.se2.glock.alpha.gameoflife;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import java.util.ArrayList;
import java.util.List;

import aau.se2.glock.alpha.gameoflife.core.AvailableServerDetails;
import aau.se2.glock.alpha.gameoflife.core.Player;
import aau.se2.glock.alpha.gameoflife.screens.MainMenuScreen;

public class GameOfLife extends Game {
	private static GameOfLife INSTANCE;

	//This is the Player-Entity of the current device
	public static Player self;
	public static List<Player> players;
	public static List<AvailableServerDetails> availableServerDetails;

	public GameOfLife(){
	}

	public static GameOfLife getInstance(){
		if(INSTANCE == null){
			INSTANCE = new GameOfLife();

			players = new ArrayList<>();
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
