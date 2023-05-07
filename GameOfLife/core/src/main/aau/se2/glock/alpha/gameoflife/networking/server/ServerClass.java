package aau.se2.glock.alpha.gameoflife.networking.server;

import com.badlogic.gdx.graphics.Color;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.Player;
import aau.se2.glock.alpha.gameoflife.networking.packages.JoinedPlayers;
import aau.se2.glock.alpha.gameoflife.networking.packages.ServerInformation;

public class ServerClass extends Listener {

    private final int UDPPORT;
    private final int TCPPORT;
    private final Server server;
    private boolean serverStarted;
    private JoinedPlayers players;
    private String hostname;

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
                e.printStackTrace();
            }
        }
    }

    private void sendServerInfoToAllTCP() {
        this.server.sendToAllTCP(new ServerInformation(this.hostname, this.TCPPORT));
    }

    public void close() {
        this.server.close();
        this.serverStarted = false;
    }

    private void sendPlayersObjectToAll() {
        this.server.sendToAllTCP(this.players);
    }

    public int getTCPport() {
        return this.TCPPORT;
    }

    public int getUDPport() {
        return this.UDPPORT;
    }


    @Override
    public void connected(Connection connection) {
        if(GameOfLife.gameStarted){
            if(this.players.getPlayers().containsKey(connection.getRemoteAddressTCP().getAddress())) {
                Player player = this.players.getPlayers().get(connection.getRemoteAddressTCP().getAddress());
                player.setOnline(true);
                this.players.addPlayer(player, connection.getRemoteAddressTCP().getAddress());
                System.out.println("[Server] Client wiederverbunden!");
                sendPlayersObjectToAll();
            }else {
                System.out.println("[Server] Client Verbindung abgelehnt da Spiel bereits läuft!");
                connection.close();
            }
        }else{
            System.out.println("[Server] Client verbunden!");
        }
    }

    @Override
    public void disconnected(Connection connection) {
        if(GameOfLife.gameStarted && this.players.getPlayers().containsKey(connection.getRemoteAddressTCP().getAddress())){
            Player player = this.players.getPlayers().get(connection.getRemoteAddressTCP().getAddress());
            player.setOnline(false);
            this.players.addPlayer(player, connection.getRemoteAddressTCP().getAddress());
            this.players.setPlayersTurn(player.getId()+1);
            sendPlayersObjectToAll();
        }
        System.out.println("[Server] Client hat Verbindung getrennt!");
    }

    @Override
    public void received(Connection connection, Object object) {

        if(object instanceof ServerInformation){
            this.server.sendToTCP(connection.getID(), new ServerInformation(this.hostname, this.TCPPORT));
        }else if (object instanceof Player) {
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
        }
    }
}