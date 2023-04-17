package com.mygdx.gameoflife.networking.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.gameoflife.core.Player;
import com.mygdx.gameoflife.networking.packages.JoinedPlayers;
import com.mygdx.gameoflife.networking.packages.PingRequest;
import com.mygdx.gameoflife.networking.packages.PingResponse;
import com.mygdx.gameoflife.networking.packages.ServerInformation;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class ServerClass extends Listener {

    private final int UDPPORT = 54777;
    private final int TCPPORT = 54333;
    private final Server server;
    private boolean serverStarted;
    private JoinedPlayers players;

    public ServerClass() {
        this.server = new Server();
        this.server.start();

        this.server.addListener(this);

        Kryo kryo = this.server.getKryo();
        kryo.register(PingRequest.class);
        kryo.register(PingResponse.class);
        kryo.register(ServerInformation.class);
        kryo.register(JoinedPlayers.class);
        kryo.register(Player.class);

        players = new JoinedPlayers();

        this.serverStarted = false;
    }

    public void start() {
        if (!this.serverStarted) {
            try {
                /*ServerSocket s = new ServerSocket(0);
                this.TCPPORT = s.getLocalPort();
                s.close();*/

                this.server.bind(this.TCPPORT, this.UDPPORT);

                this.serverStarted = true;

                //this.server.sendToAllUDP(new ServerInformation(this.TCPPORT));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
        System.out.println("[Server] Client verbunden!");
    }

    @Override
    public void disconnected(Connection connection) {
        System.out.println("[Server] Client hat Verbindung getrennt!");
    }

    @Override
    public void received(Connection connection, Object object) {

        if (object instanceof PingRequest) {

            PingResponse pingResponse = new PingResponse();

            if (((PingRequest) object).isUpdRequest() && ((PingRequest) object).isTcpPortRequest()) {
                ((PingRequest) object).setTcpPortRequest(false);
                ((PingRequest) object).setUpdRequest(false);
                pingResponse.setTcpPort(this.TCPPORT);
            }

            connection.sendTCP(pingResponse);

        } else if (object instanceof Player) {
            Player player = (Player) object;
            if (player.isJoning()) {
                player.setJoning(false);
                player.setId(this.players.getPlayerCount() + 1);
                this.players.addPlayer(player, connection.getRemoteAddressTCP().getAddress());
                return;
            } else if (player.isHasTurn()) {
                player.setHasTurn(false);
                this.players.setPlayersTurn(player.getId() + 1);
                this.players.addPlayer(player, connection.getRemoteAddressTCP().getAddress());
                return;
            }
            sendPlayersObjectToAll();
        }
    }
}