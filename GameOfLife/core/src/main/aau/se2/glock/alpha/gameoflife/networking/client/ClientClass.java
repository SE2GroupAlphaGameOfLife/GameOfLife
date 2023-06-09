package aau.se2.glock.alpha.gameoflife.networking.client;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.ClientDiscoveryHandler;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.Player;
import aau.se2.glock.alpha.gameoflife.networking.observer.ClientObserver;
import aau.se2.glock.alpha.gameoflife.networking.observer.ClientObserverSubject;
import aau.se2.glock.alpha.gameoflife.networking.packages.CheatingMessage;
import aau.se2.glock.alpha.gameoflife.networking.packages.DiscoveryResponsePacket;
import aau.se2.glock.alpha.gameoflife.networking.packages.JoinedPlayers;
import aau.se2.glock.alpha.gameoflife.networking.packages.ReportPlayerMessage;
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
                notifyObservers(GameOfLife.CREATE_SERVER_OVERVIEW_PAYLOAD);
                input.close();
            }
        }
    };

    /**
     * @param client Only for testing
     */
    public ClientClass(Client client) {
        this.client = client;
        initializeClient();
    }

    public ClientClass() {
        this.client = new Client();
        initializeClient();
    }

    private void initializeClient() {
        this.client.setDiscoveryHandler(clientDiscoveryHandler);
        this.client.start();
        this.client.addListener(this);
        Kryo kryo = client.getKryo();
        GameOfLife.registerClasses(kryo, false);
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
                notifyObservers(GameOfLife.CLIENT_CONNECTION_FAILED_PAYLOAD);
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
                notifyObservers(GameOfLife.CLIENT_CONNECTION_FAILED_PAYLOAD);
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
            newServers = new ArrayList<ServerInformation>();
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

        //Gdx.app.log("Client", "Verbunden mit Server! ");

        if (GameOfLife.getInstance().getScreen() instanceof StartGameScreen) {
            this.sendPlayerTCP(GameOfLife.self);
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
     * Sends a Player object over TCP to the server.
     *
     * @param player Player object to be sent to server.
     */
    public void sendReportPlayerTCP(Player player) {
        ReportPlayerMessage reportPlayerMessage = new ReportPlayerMessage(Integer.toString(player.getId()));
        this.client.sendTCP(reportPlayerMessage);
    }

    /**
     * Sends a Player object over TCP to the server.
     *
     * @param player Player object to be sent to server.
     */
    public void sendPlayerCheatedTCP(Player player, int cheatedAmount) {
        CheatingMessage cheatingMessage = new CheatingMessage(Integer.toString(player.getId()) + "#" + Integer.toString(player.getAge()) + "#" + Integer.toString(cheatedAmount));
        this.client.sendTCP(cheatingMessage);
    }

    /**
     * Callback method, triggered when the Client loses connection to the server.
     *
     * @param connection Represents a TCP and optionally a UDP connection between a Client and a Server.
     *                   If either underlying connection is closed or errors, both connections are closed.
     */
    @Override
    public void disconnected(Connection connection) {
        connection.close();
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
            GameOfLife.players = new ArrayList<>(((JoinedPlayers) object).getPlayers().values());
            for (Player p : GameOfLife.players) {
                if (p.getUsername().equals(GameOfLife.self.getUsername())) {
                    GameOfLife.self = p;
                    break;
                }
            }
            notifyObservers(GameOfLife.CREATE_PLAYERS_OVERVIEW_PAYLOAD);
        } else if (object instanceof String) {
            String payload = (String) object;
            if (payload.equals(GameOfLife.START_GAME_PAYLOAD)) {
                GameOfLife.gameStarted = true;
                notifyObservers(payload);
            }
        }
    }

    public Client getClient() {
        return client;
    }

    public List<ClientObserver> getClientObservers() {
        return clientObservers;
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