package aau.se2.glock.alpha.gameoflife;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.esotericsoftware.kryo.Kryo;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import aau.se2.glock.alpha.gameoflife.core.Player;
import aau.se2.glock.alpha.gameoflife.core.jobs.Job;
import aau.se2.glock.alpha.gameoflife.core.utilities.ProximitySensorInterface;
import aau.se2.glock.alpha.gameoflife.networking.client.ClientClass;
import aau.se2.glock.alpha.gameoflife.networking.packages.CheatingMessage;
import aau.se2.glock.alpha.gameoflife.networking.packages.DiscoveryResponsePacket;
import aau.se2.glock.alpha.gameoflife.networking.packages.JoinedPlayers;
import aau.se2.glock.alpha.gameoflife.networking.packages.ReportPlayerMessage;
import aau.se2.glock.alpha.gameoflife.networking.packages.ServerInformation;
import aau.se2.glock.alpha.gameoflife.networking.packages.TcpMessage;
import aau.se2.glock.alpha.gameoflife.networking.server.ServerClass;
import aau.se2.glock.alpha.gameoflife.screens.MainMenuScreen;

/**
 *
 */
public class GameOfLife extends Game {

    /**
     * File name of the Job json file located in the assets folder
     */
    public static final String FILE_JOB_JSON = "Jobs.json";

    /**
     * File name of the Event json file located in the assets folder
     */
    public static final String FILE_EVENT_JSON = "Events.json";

    /**
     *
     */
    public static final String FILE_GAMEFIELD_JSON = "Gameboard.json";
    /**
     *
     */
    public static final int TCPPORT = 54333;

    /**
     *
     */
    public static final int UDPPORT = 54777;

    /**
     *
     */
    public static final String START_GAME_PAYLOAD = "START_GAME";

    /**
     *
     */
    public static final String CREATE_SERVER_OVERVIEW_PAYLOAD = "CREATE_SERVER_OVERVIEW";

    /**
     *
     */
    public static final String CREATE_PLAYERS_OVERVIEW_PAYLOAD = "CREATE_PLAYERS_OVERVIEW", clientConnectingFailed = "CLIENT_CONNECTION_FAILED";

    /**
     *
     */
    public static ProximitySensorInterface proximitySensorInterface;

    /**
     * Player-Entity of the current device
     */
    public static Player self;

    /**
     *
     */
    public static boolean gameStarted = false;

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
    public static List<Player> players = new ArrayList<>();

    /**
     *
     */
    public static List<ServerInformation> availableServers = new ArrayList<>();

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
     * For testing only!
     *
     * @param gameOfLifeMock
     */
    public static void setInstance(GameOfLife gameOfLifeMock) {
        INSTANCE = gameOfLifeMock;
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
        setScreen(getNewMainMenuScreen());
    }

    public MainMenuScreen getNewMainMenuScreen() {
        return new MainMenuScreen();
    }

    @Override
    public void dispose() {
        proximitySensorInterface.unregisterSensor();
        super.dispose();
    }

    public static void registerClasses(Kryo kryo, boolean isUnitTest) {
        if (!isUnitTest) {
            kryo.register(SecureRandom.class);
        }
        kryo.register(JoinedPlayers.class);
        kryo.register(Color.class);
        kryo.register(Player.class);
        kryo.register(Job.class);
        kryo.register(java.util.ArrayList.class);
        kryo.register(HashMap.class);
        kryo.register(DiscoveryResponsePacket.class);
        kryo.register(TcpMessage.class);
        kryo.register(ReportPlayerMessage.class);
        kryo.register(CheatingMessage.class);
    }
}