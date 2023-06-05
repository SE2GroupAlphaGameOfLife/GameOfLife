package aau.se2.glock.alpha.gameoflife.networking.client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.ClientDiscoveryHandler;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.Player;
import aau.se2.glock.alpha.gameoflife.networking.observers.ClientObserver;
import aau.se2.glock.alpha.gameoflife.networking.observers.ClientObserverSubject;
import aau.se2.glock.alpha.gameoflife.networking.packages.DiscoveryResponsePacket;
import aau.se2.glock.alpha.gameoflife.networking.packages.JoinedPlayers;
import aau.se2.glock.alpha.gameoflife.networking.packages.ServerInformation;
import aau.se2.glock.alpha.gameoflife.screens.StartGameScreen;

/**
 * Handles Kryonet Client communication
 */
public class ClientClass implements Listener, ClientObserverSubject {

    /**
     * Kryonet Client object, for serialized network communication
     */
    private final Client client;

    private List<ServerInformation> newServers = new ArrayList<>();

    private List<ClientObserver> clientObservers = new ArrayList<>();

    private final ClientDiscoveryHandler clientDiscoveryHandler = new ClientDiscoveryHandler() {
        private Input input = null;

        @Override
        public DatagramPacket onRequestNewDatagramPacket() {
            byte[] buffer = new byte[1024];
            input = new Input(buffer);
            return new DatagramPacket(buffer, buffer.length);
        }

        @Override
        public void onDiscoveredHost(DatagramPacket datagramPacket) {
            if (input != null) {
                DiscoveryResponsePacket packet = (DiscoveryResponsePacket) client.getKryo().readClassAndObject(input);
                if (!client.isConnected()) {
                    newServers.add(new ServerInformation(packet.hostname, datagramPacket.getAddress()));
                } else {
                    onFinally();
                }
            }
        }

        @Override
        public void onFinally() {
            if (input != null) {
                GameOfLife.availableServers = newServers;
                notifyObservers(GameOfLife.createServerOverviewPayload);
                input.close();
            }
        }
    };

    /**
     * Constructor only for mock-testing purpose
     *
     * @param client Kryonet Client object, needed to override internal client parameter with mocked object.
     */
    public ClientClass(Client client) {
        this.client = client;
        this.client.setDiscoveryHandler(clientDiscoveryHandler);
        this.client.start();

        this.client.addListener(this);

        Kryo kryo = client.getKryo();
        //kryo.register(ServerInformation.class);
        kryo.register(JoinedPlayers.class);
        kryo.register(Color.class);
        kryo.register(Player.class);
        kryo.register(HashMap.class);
        kryo.register(DiscoveryResponsePacket.class);
    }

    /**
     * Default constructor used to create and start Kryonet client object,
     * add ClientClass as Listener and register classes to be serialized for network transfer.
     */
    public ClientClass() {
        this.client = new Client();
        this.client.setDiscoveryHandler(clientDiscoveryHandler);
        this.client.start();

        this.client.addListener(this);

        Kryo kryo = client.getKryo();
        //kryo.register(ServerInformation.class);
        kryo.register(JoinedPlayers.class);
        kryo.register(Color.class);
        kryo.register(Player.class);
        kryo.register(HashMap.class);
        kryo.register(DiscoveryResponsePacket.class);
    }

    public void sendMessageToServerTCP(String message) {
        this.client.sendTCP(message);
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
                //throw new RuntimeException(e);
                e.printStackTrace();
                notifyObservers(GameOfLife.clientConnectingFailed);
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
                e.printStackTrace();
                notifyObservers(GameOfLife.clientConnectingFailed);
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
        if (!this.client.isConnected()) {
            //GameOfLife.availableServers = new ArrayList<ServerInformation>();
            this.client.discoverHosts(udpPort, 3000);
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

        Gdx.app.log("Client", "Verbunden mit Server! ");

        if (GameOfLife.getInstance().getScreen().getClass().equals(StartGameScreen.class)) {
            this.sendPlayerTCP(GameOfLife.self);
        }/* else if (GameOfLife.getInstance().getScreen().getClass().equals(JoinGameScreen.class)) {
            this.client.sendTCP(new ServerInformation());
        }*/
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
        Gdx.app.log("Client", "Verbindung getrennt!");
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
        if (object instanceof JoinedPlayers) {
            Gdx.app.log("ClientClass", "Received JoinedPlayers object (" + ((JoinedPlayers) object) + ")");
            GameOfLife.players = new ArrayList<>(((JoinedPlayers) object).getPlayers().values());
            for (Player p : GameOfLife.players) {
                if (p.getUsername().equals(GameOfLife.self.getUsername())) {
                    GameOfLife.self = p;
                    break;
                }
            }
            notifyObservers(GameOfLife.createPlayersOverviewPayload);
            /*if (GameOfLife.getInstance().getScreen().getClass().equals(StartGameScreen.class)) {
                ((StartGameScreen) GameOfLife.getInstance().getScreen()).createPlayersOverview();
                Gdx.app.log("ClientClass", "Players at StartGameScreen (" + GameOfLife.players + ")");
            }*/
        } else if (object instanceof String) {
            String payload = (String) object;
            if (payload.equals(GameOfLife.startGamePayload)) {
                Gdx.app.log("ClientClass/Received", "StartGamePayload received!");
                GameOfLife.gameStarted = true;
                notifyObservers(payload);
            }
        }
    }

    public Client getClient() {
        return client;
    }

    @Override
    public void registerObserver(ClientObserver observer) {
        clientObservers.add(observer);
    }

    @Override
    public void removeObserver(ClientObserver observer) {
        clientObservers.remove(observer);
    }

    @Override
    public void notifyObservers(String payload) {
        for (ClientObserver observer : clientObservers) {
            observer.update(payload);
        }
    }
}