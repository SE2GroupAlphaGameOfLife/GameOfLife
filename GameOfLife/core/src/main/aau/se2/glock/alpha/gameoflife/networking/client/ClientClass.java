package aau.se2.glock.alpha.gameoflife.networking.client;

import com.badlogic.gdx.Game;
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

public class ClientClass extends Listener {

    private final Client client;

    // Constructor with a Client argument for testing
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
        /*if(GameOfLife.availableServers.isEmpty()){
            List<ServerInformation> serverDetails = new ArrayList<>();
            serverDetails.add(new ServerInformation("Host1", 1));
            serverDetails.add(new ServerInformation("Host2", 2));
            serverDetails.add(new ServerInformation("Host3", 3));
            serverDetails.add(new ServerInformation("Host4", 4));
            serverDetails.add(new ServerInformation("Host5", 5));
            serverDetails.add(new ServerInformation("Host6", 6));
            GameOfLife.availableServers = serverDetails;
        }*/
    }

    @Override
    public void connected(Connection connection) {

        System.out.println("[Client] Verbunden!");

        if (GameOfLife.getInstance().getScreen().getClass().equals(StartGameScreen.class)) {
            this.sendPlayerTCP(GameOfLife.self);
        }else if(GameOfLife.getInstance().getScreen().getClass().equals(JoinGameScreen.class) || GameOfLife.getInstance().getScreen().getClass().equals(MainMenuScreen.class)){
            this.client.sendTCP(new ServerInformation());
        }
    }

    public void sendPlayerTCP(Player player) {
        this.client.sendTCP(player);
    }

    @Override
    public void disconnected(Connection connection) {
        System.out.println("[Client] Verbindung getrennt!");
    }

    @Override
    public void received(Connection connection, Object object) {

        if (object instanceof ServerInformation) {
            ServerInformation serverInformation = (ServerInformation) object;
            if (!GameOfLife.gameStarted && (GameOfLife.getInstance().getScreen().getClass().equals(JoinGameScreen.class) || GameOfLife.getInstance().getScreen().getClass().equals(MainMenuScreen.class))) {
                serverInformation.setAddress(connection.getRemoteAddressTCP().getAddress());

                if (!GameOfLife.availableServers.contains(serverInformation)) {
                    GameOfLife.availableServers.add(serverInformation);
                    //System.out.println("[Client] " + connection.getRemoteAddressTCP().getAddress() + ":" + serverInformation.getTcpPort());
                    //System.out.println("INOOOOFOOFFO "+serverInformation);
                    this.client.close();
                    ((JoinGameScreen)GameOfLife.getInstance().getScreen()).createServerOverview();
                    //GameOfLife.getInstance().render();
                }
            }

            return;
        } else if (object instanceof JoinedPlayers) {
            if (GameOfLife.getInstance().getScreen().getClass().equals(StartGameScreen.class)) {
                ((StartGameScreen) GameOfLife.getInstance().getScreen()).createPlayersOverview();
            }
            GameOfLife.players = new ArrayList<>(((JoinedPlayers) object).getPlayers().values());
            //GameOfLife.getInstance().render();

            return;
        }
    }
}
