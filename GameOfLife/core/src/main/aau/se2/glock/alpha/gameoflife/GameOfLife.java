package aau.se2.glock.alpha.gameoflife;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import java.util.ArrayList;
import java.util.List;

import aau.se2.glock.alpha.gameoflife.core.Player;
import aau.se2.glock.alpha.gameoflife.core.utilities.ProximitySensorInterface;
import aau.se2.glock.alpha.gameoflife.networking.client.ClientClass;
import aau.se2.glock.alpha.gameoflife.networking.packages.ServerInformation;
import aau.se2.glock.alpha.gameoflife.networking.server.ServerClass;
import aau.se2.glock.alpha.gameoflife.screens.MainMenuScreen;

/**
 *
 */
public class GameOfLife extends Game {


    public static ProximitySensorInterface proximitySensorInterface;

    /**
     *
     */
    public static final int TCPPORT = 54333;

    /**
     *
     */
    public static final int UDPPORT = 54777;

    /**
     * Player-Entity of the current device
     */
    public static Player self;

    /**
     *
     */
    public static boolean gameStarted;

    /**
     *
     */
    public static ServerClass server;

    /**
     *
     */
    public static ClientClass client;

    /**
     *
     */
    public static List<Player> players;

    /**
     *
     */
    public static List<ServerInformation> availableServers;

    /**
     *
     */
    private static GameOfLife INSTANCE;

    /**
     * @return
     */
    public static GameOfLife getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameOfLife();

            players = new ArrayList<>();
            server = new ServerClass(TCPPORT, UDPPORT);
            client = new ClientClass();

            gameStarted = false;
            availableServers = new ArrayList<>();
        }

        return INSTANCE;
    }

    /**
     * @param screen
     */
    public static void changeScreen(Screen screen) {
        INSTANCE.setScreen(screen);
    }

    /**
     * @param gu
     */
    //For Testing only
    public static void changeInstance(GameOfLife gu) {
        INSTANCE = gu;
    }

    /**
     * @return
     */
    //For testing only
    public static boolean isGameStarted() {
        return gameStarted;
    }

    /**
     *
     */
    @Override
    public void create() {
        proximitySensorInterface.registerSensor();
        setScreen(new MainMenuScreen());
    }

    @Override
    public void dispose() {
        proximitySensorInterface.unregisterSensor();
        super.dispose();
    }
}