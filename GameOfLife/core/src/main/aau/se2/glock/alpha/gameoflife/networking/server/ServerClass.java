package aau.se2.glock.alpha.gameoflife.networking.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.ServerDiscoveryHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.List;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.Player;
import aau.se2.glock.alpha.gameoflife.core.logic.PlayerCheated;
import aau.se2.glock.alpha.gameoflife.core.utilities.PlayerColor;
import aau.se2.glock.alpha.gameoflife.networking.packages.CheatingVisitor;
import aau.se2.glock.alpha.gameoflife.networking.packages.DiscoveryResponsePacket;
import aau.se2.glock.alpha.gameoflife.networking.packages.JoinedPlayers;
import aau.se2.glock.alpha.gameoflife.networking.packages.ReportPlayerVisitor;
import aau.se2.glock.alpha.gameoflife.networking.packages.TcpMessage;
import aau.se2.glock.alpha.gameoflife.networking.packages.TcpMessageVisitor;

/**
 *
 */
public class ServerClass implements Listener {

    /**
     * The port used for UDP connection
     */
    private int UDPPORT;

    /**
     * The port used for TCP connection
     */
    private int TCPPORT;

    /**
     * Kryonet server object, for networking
     */
    private Server server;

    /**
     * Indicates if server is running on specified ports
     */
    private boolean serverStarted;

    /**
     * All players currently in the game
     */
    private JoinedPlayers players;

    /**
     * Name of the player hosting the game
     */
    private String hostname;

    /**
     *
     */
    private List<PlayerCheated> playerCheatedList;
    /**
     * Custom UDP broadcast discovery answer. Returns hostname
     */
    private ServerDiscoveryHandler serverDiscoveryHandler = new ServerDiscoveryHandler() {
        @Override
        public boolean onDiscoverHost(DatagramChannel datagramChannel, InetSocketAddress fromAddress) throws IOException {

            DiscoveryResponsePacket packet = new DiscoveryResponsePacket();
            packet.hostname = hostname;

            ByteBuffer buffer = ByteBuffer.allocate(256);
            getClient().getSerialization().write(null, buffer, packet);
            buffer.flip();

            datagramChannel.send(buffer, fromAddress);

            return true;
        }
    };

    /**
     * @param TCPPORT
     * @param UDPPORT
     */
    public ServerClass(int TCPPORT, int UDPPORT) {
        this.server = new Server();
        initializeServer(TCPPORT, UDPPORT, false);
    }

    /**
     * For testing only
     *
     * @param TCPPORT
     * @param UDPPORT
     */
    public ServerClass(int TCPPORT, int UDPPORT, Server test) {
        this.server = test;
        initializeServer(TCPPORT, UDPPORT, true);
    }

    private void initializeServer(int TCPPORT, int UDPPORT, boolean isTest) {
        this.TCPPORT = TCPPORT;
        this.UDPPORT = UDPPORT;

        this.server.addListener(this);
        this.server.setDiscoveryHandler(serverDiscoveryHandler);

        Kryo kryo = this.server.getKryo();
        GameOfLife.registerClasses(kryo, isTest);

        players = new JoinedPlayers();

        this.serverStarted = false;
    }

    protected Client getClient() {
        return GameOfLife.client.getClient();
    }

    /**
     * @param hostname
     */
    public void start(String hostname) {
        if (!this.serverStarted) {
            this.hostname = hostname;
            try {
                this.server.bind(this.TCPPORT, this.UDPPORT);
                this.server.start();

                this.serverStarted = true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * For testing only
     *
     * @return
     */
    public ServerDiscoveryHandler getServerDiscoveryHandler() {
        return serverDiscoveryHandler;
    }

    /**
     *
     */
    public void close() {
        this.server.close();
        this.serverStarted = false;
    }

    /**
     *
     */
    public void sendPlayersObjectToAll() {
        this.server.sendToAllTCP(this.players);
    }

    public void sendMessageToAll(String message) {
        this.server.sendToAllTCP(message);
    }

    /**
     * @param connection
     */
    @Override
    public void connected(Connection connection) {
        if (GameOfLife.gameStarted) {
            if (this.players.getPlayers().containsKey(connection.getID())) {
                Player player = this.players.getPlayers().get(connection.getID());
                player.setOnline(true);
                player.setJoning(false);
                player.setHasTurn(false);
                this.players.addPlayer(player, connection.getID());
                //Gdx.app.log("Server", "Client wiederverbunden!");
                sendPlayersObjectToAll();
            } else {
                connection.close();
            }
        }
    }

    /**
     * @param connection
     */
    @Override
    public void disconnected(Connection connection) {
        if (GameOfLife.gameStarted && this.players.getPlayers().containsKey(connection.getID())) {
            Player player = this.players.getPlayers().get(connection.getID());
            player.setOnline(false);
            player.setJoning(true);
            this.players.addPlayer(player, connection.getID());
            if (player.hasTurn()) {
                this.players.setPlayersTurn(player.getId() + 1);
            }
            sendPlayersObjectToAll();
            return;
        } else if ((!GameOfLife.gameStarted) && this.players.getPlayers().containsKey(connection.getID())) {
            this.players.removePlayerWithConnectionID(connection.getID());
            sendPlayersObjectToAll();
        }
        connection.close();
        //Gdx.app.log("Server", "Client hat Verbindung getrennt!");
    }

    /**
     * @param connection
     * @param object
     */
    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof Player) {
            handleReceivedPlayer(connection, (Player) object);
        } else if (object instanceof String) {
            handleReceivedString((String) object);
        } else if (object instanceof TcpMessage) {
            handleReceivedTcpMessage((TcpMessage) object);
        }
    }

    private void handleReceivedPlayer(Connection connection, Player player) {
        if (!GameOfLife.gameStarted && player.isJoning()) {
            player.setJoning(false);
            player.setId(this.players.getPlayerCount() + 1);
            player.setPosition(this.players.getPlayerCount() * 3);
            switch (this.players.getPlayerCount()) {
                case 1:
                    player.setColor(PlayerColor.PURPLE);
                    break;
                case 2:
                    player.setColor(PlayerColor.GREEN);
                    break;
                case 3:
                    player.setColor(PlayerColor.YELLOW);
                    break;
            }
            this.players.addPlayer(player, connection.getID());
        }
        if (GameOfLife.gameStarted && player.hasTurn() && player.isOnline()) {
            this.players.addPlayer(player, connection.getID());
            this.players.setPlayersTurn(player.getId() + 1);
        }
        this.sendPlayersObjectToAll();
    }

    private void handleReceivedString(String payload) {
        if (payload.equals(GameOfLife.START_GAME_PAYLOAD)) {
            this.sendMessageToAll(payload);
        }
        sendPlayersObjectToAll();
    }

    private void handleReceivedTcpMessage(TcpMessage message) {
        TcpMessageVisitor reportVisitor = new ReportPlayerVisitor();
        TcpMessageVisitor cheatingVisitor = new CheatingVisitor();

        message.accept(reportVisitor);
        message.accept(cheatingVisitor);
    }


    /**
     * @return
     */
    public int getUDPPORT() {
        return UDPPORT;
    }

    /**
     * @return
     */
    public int getTCPPORT() {
        return TCPPORT;
    }

    /**
     * @return
     */
    public boolean isServerStarted() {
        return serverStarted;
    }

    /**
     * @return
     */
    public JoinedPlayers getPlayers() {
        return players;
    }

    /**
     * @param joinedPlayers
     */
    //For testing only
    public void setPlayers(JoinedPlayers joinedPlayers) {
        this.players = joinedPlayers;
    }

    /**
     * @return
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * @param hostname
     */
    //For testing only
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /**
     * @return
     */
    public Server getServer() {
        return server;
    }

    /**
     * @param server
     */
    //For testing only
    public void setServer(Server server) {
        this.server = server;
    }

    public List<PlayerCheated> getPlayerCheatedList() {
        if (playerCheatedList == null) {
            playerCheatedList = new ArrayList<>();
        }

        return playerCheatedList;
    }

    public void setPlayerCheatedList(List<PlayerCheated> playerCheatedList) {
        this.playerCheatedList = playerCheatedList;
    }
}