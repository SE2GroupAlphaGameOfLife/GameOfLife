package aau.se2.glock.alpha.gameoflife.networking.server;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.ServerDiscoveryHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.Player;
import aau.se2.glock.alpha.gameoflife.core.jobs.Job;
import aau.se2.glock.alpha.gameoflife.core.logic.PlayerCheated;
import aau.se2.glock.alpha.gameoflife.networking.packages.CheatingMessage;
import aau.se2.glock.alpha.gameoflife.networking.packages.CheatingVisitor;
import aau.se2.glock.alpha.gameoflife.networking.packages.DiscoveryResponsePacket;
import aau.se2.glock.alpha.gameoflife.networking.packages.JoinedPlayers;
import aau.se2.glock.alpha.gameoflife.networking.packages.ReportPlayerMessage;
import aau.se2.glock.alpha.gameoflife.networking.packages.ReportPlayerVisitor;
import aau.se2.glock.alpha.gameoflife.networking.packages.ServerInformation;
import aau.se2.glock.alpha.gameoflife.networking.packages.TcpMessage;
import aau.se2.glock.alpha.gameoflife.networking.packages.TcpMessageVisitor;

/**
 *
 */
public class ServerClass implements Listener {

    /**
     *
     */
    private final int UDPPORT;

    /**
     *
     */
    private final int TCPPORT;

    /**
     *
     */
    private Server server;

    /**
     *
     */
    private boolean serverStarted;

    /**
     *
     */
    private JoinedPlayers players;

    /**
     *
     */
    private String hostname;

    /**
     *
     */
    private List<PlayerCheated> playerCheatedList;

    private ServerDiscoveryHandler serverDiscoveryHandler = new ServerDiscoveryHandler() {
        @Override
        public boolean onDiscoverHost(DatagramChannel datagramChannel, InetSocketAddress fromAddress) throws IOException {

            DiscoveryResponsePacket packet = new DiscoveryResponsePacket();
            packet.hostname = hostname;

            ByteBuffer buffer = ByteBuffer.allocate(256);
            GameOfLife.client.getClient().getSerialization().write(null, buffer, packet);
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

        this.TCPPORT = TCPPORT;
        this.UDPPORT = UDPPORT;

        this.server.addListener(this);
        this.server.setDiscoveryHandler(serverDiscoveryHandler);

        Kryo kryo = this.server.getKryo();
        this.registerClasses(kryo);

        players = new JoinedPlayers();

        this.serverStarted = false;
    }

    public void registerClasses(Kryo kryo) {
        kryo.register(SecureRandom.class);
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
                //sendServerInfoToAllTCP();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     *
     */
    private void sendServerInfoToAllTCP() {
        this.server.sendToAllTCP(new ServerInformation(this.hostname, this.TCPPORT));
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
    private void sendPlayersObjectToAll() {
        this.server.sendToAllTCP(this.players);
    }

    private void sendMessageToAll(String message) {
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
                this.players.addPlayer(player, connection.getID());
                Gdx.app.log("Server", "Client wiederverbunden!");
                sendPlayersObjectToAll();
            } else {
                Gdx.app.log("Server", "Client Verbindung abgelehnt da Spiel bereits läuft!");
                connection.close();
            }
        } else {
            Gdx.app.log("Server", "Client verbunden!");
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
            players.removePlayerWithConnectionID(connection.getID());
            sendPlayersObjectToAll();
        }
        connection.close();
        Gdx.app.log("Server", "Client hat Verbindung getrennt!");
    }

    /**
     * @param connection
     * @param object
     */
    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof Player) {
            Player player = (Player) object;
            Gdx.app.log("ServerClass", "Received Player object (" + player + ")");
            if (!GameOfLife.gameStarted && player.isJoning()) {
                player.setJoning(false);
                player.setId(this.players.getPlayerCount() + 1);
                this.players.addPlayer(player, connection.getID());
                //return;
            }
            if (GameOfLife.gameStarted && player.hasTurn() && player.isOnline()/* && this.players.getPlayers().containsKey(connection.getRemoteAddressTCP().getAddress())*/) {
                //player.setHasTurn(false);
                this.players.setPlayersTurn(player.getId() + 1);
                this.players.addPlayer(player, connection.getID());
                //return;
            }
            this.sendPlayersObjectToAll();
        } else if (object instanceof String) {
            String payload = (String) object;
            if (payload.equals(GameOfLife.START_GAME_PAYLOAD)) {
                Gdx.app.log("ServerClass/Received", "StartGamePayload received!");
                this.sendMessageToAll(payload);
            }
            sendPlayersObjectToAll();
        } else if (object instanceof TcpMessage) { //This is for the cheating functionality
            TcpMessageVisitor reportVisitor = new ReportPlayerVisitor();
            TcpMessageVisitor cheatingVisitor = new CheatingVisitor();

            ((TcpMessage) object).accept(reportVisitor);
            ((TcpMessage) object).accept(cheatingVisitor);
        }
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
    //For testing only
    public boolean isServerStarted() {
        return serverStarted;
    }

    /**
     * @return
     */
    //For testing only
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
        return playerCheatedList;
    }

    public void setPlayerCheatedList(List<PlayerCheated> playerCheatedList) {
        this.playerCheatedList = playerCheatedList;
    }
}