package aau.se2.glock.alpha.gameoflife.networking.client;

import com.badlogic.gdx.graphics.Color;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.Player;
import aau.se2.glock.alpha.gameoflife.networking.packages.JoinedPlayers;
import aau.se2.glock.alpha.gameoflife.networking.packages.ServerInformation;
import aau.se2.glock.alpha.gameoflife.screens.JoinGameScreen;
import aau.se2.glock.alpha.gameoflife.screens.MainMenuScreen;
import aau.se2.glock.alpha.gameoflife.screens.StartGameScreen;

/**
 * Handles Kryonet Client communication
 */
public class ClientClass extends Listener {

    /**
     * Kryonet Client object, for serialized network communication
     */
    private final Client client;

    /**
     * Constructor only for mock-testing purpose
     *
     * @param client Kryonet Client object, needed to override internal client parameter with mocked object.
     */
    public ClientClass(Client client) {
        this.client = client;
        this.client.start();

        this.client.addListener(this);

        Kryo kryo = client.getKryo();
        kryo.register(ServerInformation.class);
        kryo.register(JoinedPlayers.class);
        kryo.register(Color.class);
        kryo.register(Player.class);
    }

    /**
     * Default constructor used to create and start Kryonet client object,
     * add ClientClass as Listener and register classes to be serialized for network transfer.
     */
    public ClientClass() {
        this.client = new Client();
        this.client.start();

        this.client.addListener(this);

        Kryo kryo = client.getKryo();
        kryo.register(ServerInformation.class);
        kryo.register(JoinedPlayers.class);
        kryo.register(Color.class);
        kryo.register(Player.class);
    }

    /**
     * Tries to connect to server, specified by parameters
     *
     * @param address IPAddress of the server
     * @param tcpPort TCP port of the server
     * @param udpPort UDP port of the server
     */
    public void connect(InetAddress address, int tcpPort, int udpPort) {
        if (!this.client.isConnected()) {
            try {
                this.client.connect(5000, address, tcpPort, udpPort);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Tries to connect to server, specified by parameters
     *
     * @param address IPAddress of the server
     * @param tcpPort TCP port of the server
     * @param udpPort UDP port of the server
     */
    public void connect(String address, int tcpPort, int udpPort) {
        if (!this.client.isConnected()) {
            try {
                this.client.connect(5000, address, tcpPort, udpPort);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Closes client object, forcing it disconnect from server if connected to any
     */
    public void disconnect() {
        this.client.close();
    }

    /**
     * Sends a UDP broadcast message, to discover available servers in network.
     * When a server has been found, the client sends a TCP message, to request
     * further server information.
     * Servers that have already been found previously, are not contacted again,
     * to reduce system load.
     *
     * @param udpPort UDP port of the server(s)
     */
    public void discoverServers(int udpPort) {
        List<InetAddress> servers = new ArrayList<InetAddress>();

        ArrayList<ServerInformation> toKeep = new ArrayList<ServerInformation>();

        for (InetAddress a : this.client.discoverHosts(udpPort, 5000)) {
            if (!servers.contains(a))
                servers.add(a);
        }
        for (ServerInformation s : GameOfLife.availableServers) {
            if (servers.contains(s.getAddress())) {
                servers.remove(s.getAddress());
                toKeep.add(s);
            }
        }
        GameOfLife.availableServers = toKeep;

        this.client.close();
        for (InetAddress a : servers) {
            this.client.start();
            this.connect(a, GameOfLife.TCPPORT, GameOfLife.UDPPORT);
        }
        this.client.start();

        if(!servers.isEmpty()){
            ((JoinGameScreen) GameOfLife.getInstance().getScreen()).createServerOverview();
        }
    }

    /**
     * Callback method, which triggers, if a connection to a server was successfully.
     * If on JoinGameScreen, further server information are requested over TCP.
     * If on StartGameScreen, the Player object is sent to the server (server registers player).
     *
     * @param connection Represents a TCP and optionally a UDP connection between a Client and a Server.
     *                   If either underlying connection is closed or errors, both connections are closed.
     */
    @Override
    public void connected(Connection connection) {

        System.out.println("[Client] Verbunden!");

        if (GameOfLife.getInstance().getScreen().getClass().equals(StartGameScreen.class)) {
            this.sendPlayerTCP(GameOfLife.self);
        } else if (GameOfLife.getInstance().getScreen().getClass().equals(JoinGameScreen.class)) {
            this.client.sendTCP(new ServerInformation());
        }
    }

    /**
     * Sends a Player object over TCP to the server.
     *
     * @param player Player object to be sent to server.
     */
    public void sendPlayerTCP(Player player) {
        this.client.sendTCP(player);
    }

    /**
     * Callback method, triggered when the Client loses connection to the server.
     *
     * @param connection Represents a TCP and optionally a UDP connection between a Client and a Server.
     *                   If either underlying connection is closed or errors, both connections are closed.
     */
    @Override
    public void disconnected(Connection connection) {
        System.out.println("[Client] Verbindung getrennt!");
    }

    /**
     * Callback method, triggered when a package from the server has been received.
     * Dependent on the class instance received, UI and static lists like availableServers or players from
     * the GameOfLife class are refreshed.
     *
     * @param connection Represents a TCP and optionally a UDP connection between a Client and a Server.
     *                   If either underlying connection is closed or errors, both connections are closed.
     * @param object     An object as instance of the received class.
     */
    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof ServerInformation) {
            ServerInformation serverInformation = (ServerInformation) object;
            if (!GameOfLife.gameStarted && (GameOfLife.getInstance().getScreen().getClass().equals(JoinGameScreen.class) || GameOfLife.getInstance().getScreen().getClass().equals(MainMenuScreen.class))) {
                serverInformation.setAddress(connection.getRemoteAddressTCP().getAddress());

                if (!GameOfLife.availableServers.contains(serverInformation)) {
                    GameOfLife.availableServers.add(serverInformation);
                    this.client.close();
                    ((JoinGameScreen) GameOfLife.getInstance().getScreen()).createServerOverview();
                }
            }

        } else if (object instanceof JoinedPlayers) {
            GameOfLife.players = new ArrayList<>(((JoinedPlayers) object).getPlayers().values());
            if (GameOfLife.getInstance().getScreen().getClass().equals(StartGameScreen.class)) {
                ((StartGameScreen) GameOfLife.getInstance().getScreen()).createPlayersOverview();
            }
        }
    }
}
