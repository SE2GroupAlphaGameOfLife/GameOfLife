package com.mygdx.gameoflife;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.mygdx.gameoflife.core.Player;
import com.mygdx.gameoflife.networking.client.ClientClass;
import com.mygdx.gameoflife.networking.packages.ServerInformation;
import com.mygdx.gameoflife.networking.server.ServerClass;
import com.mygdx.gameoflife.screens.MainMenuScreen;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class GameOfLife extends Game {
	private static GameOfLife INSTANCE;

	//This is the Player-Entity of the current device
	public static Player self;

	public static boolean gameStarted;

	public static ServerClass server;
	public static ClientClass client;
	public static List<Player> players;
	public static List<ServerInformation> availableServers;
	public static final int TCPPORT = 54333;
	public static final int UDPPORT = 54777;

	public static GameOfLife getInstance(){
		if(INSTANCE == null){
			INSTANCE = new GameOfLife();

			players = new ArrayList<>();
			server = new ServerClass(TCPPORT, UDPPORT);
			client = new ClientClass();

			gameStarted = false;
			availableServers = new ArrayList<>();
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
