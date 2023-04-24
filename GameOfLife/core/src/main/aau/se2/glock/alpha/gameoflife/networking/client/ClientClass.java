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
import aau.se2.glock.alpha.gameoflife.networking.packages.PingRequest;
import aau.se2.glock.alpha.gameoflife.networking.packages.PingResponse;
import aau.se2.glock.alpha.gameoflife.networking.packages.ServerInformation;
import aau.se2.glock.alpha.gameoflife.screens.JoinGameScreen;

public class ClientClass extends Listener {

    private final Client client;

    public ClientClass() {
        this.client = new Client();
        this.client.start();

        this.client.addListener(this);

        Kryo kryo = client.getKryo();
        kryo.register(PingRequest.class);
        kryo.register(PingResponse.class);
        kryo.register(ServerInformation.class);
        kryo.register(JoinedPlayers.class);
        kryo.register(Color.class);
        kryo.register(Player.class);
    }

    public void connect(InetAddress address, int tcpPort, int udpPort) {
        if (!this.client.isConnected()) {
            try {
                this.client.connect(5000, address, tcpPort, udpPort);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void connect(String address, int tcpPort, int udpPort) {
        if (!this.client.isConnected()) {
            try {
                this.client.connect(5000, address, tcpPort, udpPort);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void disconnect() {
        this.client.close();
    }

    public void discoverServers(int udpPort) {
        List<InetAddress> servers = new ArrayList<InetAddress>();

        GameOfLife.availableServers = new ArrayList<ServerInformation>();

        for (InetAddress a : this.client.discoverHosts(udpPort, 5000)) {
            if (!servers.contains(a))
                servers.add(a);
        }
        //hdf

        this.client.close();
        for(InetAddress a : servers){
            this.client.start();
            this.connect(a, GameOfLife.TCPPORT, GameOfLife.UDPPORT);
            this.client.sendTCP(new ServerInformation());
            this.client.close();
        }
        this.client.start();
        if(servers.isEmpty()){
            List<ServerInformation> serverDetails = new ArrayList<>();
            serverDetails.add(new ServerInformation("Host1", 1));
            serverDetails.add(new ServerInformation("Host2", 2));
            serverDetails.add(new ServerInformation("Host3", 3));
            serverDetails.add(new ServerInformation("Host4", 4));
            serverDetails.add(new ServerInformation("Host5", 5));
            serverDetails.add(new ServerInformation("Host6", 6));
            GameOfLife.availableServers = serverDetails;
        }
    }

    public void sendTCP(PingRequest pingRequest) {
        this.client.sendTCP(pingRequest);
    }

    @Override
    public void connected(Connection connection) {

        System.out.println("[Client] Verbunden!");

        this.client.sendTCP(GameOfLife.self);

    }

    @Override
    public void disconnected(Connection connection) {
        System.out.println("[Client] Verbindung getrennt!");
    }

    @Override
    public void received(Connection connection, Object object) {

        if (object instanceof PingResponse) {

            PingResponse pingResponse = (PingResponse) object;
            return;
        } else if (object instanceof ServerInformation) {
            ServerInformation serverInformation = (ServerInformation) object;
            if (!GameOfLife.gameStarted && GameOfLife.getInstance().getScreen().getClass().equals(JoinGameScreen.class)) {
                serverInformation.setAddress(connection.getRemoteAddressTCP().getAddress());

                if (!GameOfLife.availableServers.contains(serverInformation)) {
                    GameOfLife.availableServers.add(serverInformation);
                    System.out.println("[Client] " + connection.getRemoteAddressTCP().getAddress() + ":" + serverInformation.getTcpPort());
                    //GameOfLife.getInstance().render();
                }
            }

            return;
        } else if (object instanceof JoinedPlayers) {
            GameOfLife.players = new ArrayList<>(((JoinedPlayers) object).getPlayers().values());
            //GameOfLife.getInstance().render();

            return;
        }
    }
}
