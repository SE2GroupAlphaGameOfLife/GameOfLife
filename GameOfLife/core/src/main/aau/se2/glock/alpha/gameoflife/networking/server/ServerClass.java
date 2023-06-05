package aau.se2.glock.alpha.gameoflife.networking.server;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.Player;
import aau.se2.glock.alpha.gameoflife.core.logic.PlayerCheated;
import aau.se2.glock.alpha.gameoflife.networking.packages.JoinedPlayers;
import aau.se2.glock.alpha.gameoflife.networking.packages.ServerInformation;
import aau.se2.glock.alpha.gameoflife.networking.packages.TCPCommands;

/**
 *
 */
public class ServerClass extends Listener {

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

    private List<PlayerCheated> playerCheatedList;

    /**
     * @param TCPPORT
     * @param UDPPORT
     */
    public ServerClass(int TCPPORT, int UDPPORT) {
        this.server = new Server();

        this.TCPPORT = TCPPORT;
        this.UDPPORT = UDPPORT;

        this.server.addListener(this);

        Kryo kryo = this.server.getKryo();
        kryo.register(ServerInformation.class);
        kryo.register(JoinedPlayers.class);
        kryo.register(Color.class);
        kryo.register(Player.class);

        players = new JoinedPlayers();

        this.serverStarted = false;
    }

    /**
     * @param hostname
     */
    public void start(String hostname) {
        if (!this.serverStarted) {
            try {
                /*ServerSocket s = new ServerSocket(0);
                this.TCPPORT = s.getLocalPort();
                s.close();*/
                this.server.start();
                this.server.bind(this.TCPPORT, this.UDPPORT);

                this.serverStarted = true;

                this.hostname = hostname;
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
        if (GameOfLife.gameStarted && this.players.getPlayers().containsKey(connection.getRemoteAddressTCP().getAddress())) {
            Player player = this.players.getPlayers().get(connection.getRemoteAddressTCP().getAddress());
            player.setOnline(false);
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

        if (object instanceof ServerInformation) {
            this.server.sendToTCP(connection.getID(), new ServerInformation(this.hostname, this.TCPPORT));
        } else if (object instanceof Player) {
            Player player = (Player) object;
            if (!GameOfLife.gameStarted && player.isJoning()) {
                player.setJoning(false);
                player.setId(this.players.getPlayerCount() + 1);
                this.players.addPlayer(player, connection.getRemoteAddressTCP().getAddress());
                return;
            }
            if (GameOfLife.gameStarted && player.isHasTurn()/* && this.players.getPlayers().containsKey(connection.getRemoteAddressTCP().getAddress())*/) {
                player.setHasTurn(false);
                this.players.setPlayersTurn(player.getId() + 1);
                this.players.addPlayer(player, connection.getRemoteAddressTCP().getAddress());
                return;
            }
            sendPlayersObjectToAll();
        } else if(object instanceof String){ //This is for the cheating functionality
            String command = ((String) object).split("#")[0];
            int playerId = Integer.parseInt(((String) object).split("#")[1]);

            if(command.equals(TCPCommands.REPORTED)){ //if someone reports a player the server has to check if this player has cheated in the last few rounds
                for (PlayerCheated playerCheated : playerCheatedList) {
                    if(playerCheated.getPlayerId() == playerId){
                        Player player = players.getPlayers().get(playerId);
                        if(player.getAge() <= playerCheated.getCheatedAtAge() + 5){
                            player.setPosition(player.getPosition() - playerCheated.getAmountCheated());
                            this.sendPlayersObjectToAll();

                            return;
                        }
                    }
                }
            } else if(command.equals(TCPCommands.CHEATED)){ //When a player has cheated, the server is notified, so we can save the event
                int cheatedAmount = Integer.parseInt(((String) object).split("#")[2]);
                Player player = players.getPlayers().get(playerId);
                playerCheatedList.add(new PlayerCheated(player.getAge(), player.getId(), cheatedAmount));
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