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
import java.util.HashMap;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.Player;
import aau.se2.glock.alpha.gameoflife.networking.packages.DiscoveryResponsePacket;
import aau.se2.glock.alpha.gameoflife.networking.packages.JoinedPlayers;
import aau.se2.glock.alpha.gameoflife.networking.packages.ServerInformation;

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
        //kryo.register(ServerInformation.class);
        kryo.register(JoinedPlayers.class);
        kryo.register(Color.class);
        kryo.register(Player.class);
        kryo.register(HashMap.class);
        kryo.register(DiscoveryResponsePacket.class);

        players = new JoinedPlayers();

        this.serverStarted = false;
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
     * @return
     */
    public int getTCPport() {
        return this.TCPPORT;
    }

    /**
     * @return
     */
    public int getUDPport() {
        return this.UDPPORT;
    }

    /**
     * @param connection
     */
    @Override
    public void connected(Connection connection) {
        if (GameOfLife.gameStarted) {
            if (this.players.getPlayers().containsKey(connection.getRemoteAddressTCP().getAddress())) {
                Player player = this.players.getPlayers().get(connection.getRemoteAddressTCP().getAddress());
                player.setOnline(true);
                player.setJoning(false);
                this.players.addPlayer(player, connection.getRemoteAddressTCP().getAddress());
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
        if (GameOfLife.gameStarted && this.players.getPlayers().containsKey(connection.getRemoteAddressTCP().getAddress().toString())) {
            Player player = this.players.getPlayers().get(connection.getRemoteAddressTCP().getAddress().toString());
            player.setOnline(false);
            player.setJoning(true);
            player.setHasTurn(false);
            this.players.addPlayer(player, connection.getRemoteAddressTCP().getAddress());
            this.players.setPlayersTurn(player.getId() + 1);
            sendPlayersObjectToAll();
        }
        Gdx.app.log("Server", "Client hat Verbindung getrennt!");
        //System.out.println("[Server] Client hat Verbindung getrennt!");
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
                this.players.addPlayer(player, connection.getRemoteAddressTCP().getAddress());
                //return;
            }
            if (GameOfLife.gameStarted && player.hasTurn() && player.isOnline()/* && this.players.getPlayers().containsKey(connection.getRemoteAddressTCP().getAddress())*/) {
                player.setHasTurn(false);
                this.players.setPlayersTurn(player.getId() + 1);
                this.players.addPlayer(player, connection.getRemoteAddressTCP().getAddress());
                //return;
            }
            this.sendPlayersObjectToAll();
        } else if (object instanceof String) {
            String payload = (String) object;
            if (payload.equals(GameOfLife.startGamePayload)) {
                Gdx.app.log("ServerClass/Received", "StartGamePayload received!");
                this.sendMessageToAll(payload);
            }
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
}