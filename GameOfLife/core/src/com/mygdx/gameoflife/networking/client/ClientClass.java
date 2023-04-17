package com.mygdx.gameoflife.networking.client;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.gameoflife.GameOfLife;
import com.mygdx.gameoflife.core.Player;
import com.mygdx.gameoflife.networking.packages.JoinedPlayers;
import com.mygdx.gameoflife.networking.packages.PingRequest;
import com.mygdx.gameoflife.networking.packages.PingResponse;
import com.mygdx.gameoflife.networking.packages.ServerInformation;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

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

    public void disconnect(){
        this.client.close();
    }

    public List<InetAddress> discoverServers(int udpPort) {
        List<InetAddress> servers = new ArrayList<InetAddress>();

        for (InetAddress a : this.client.discoverHosts(udpPort, 5000)) {
            if (!servers.contains(a))
                servers.add(a);
        }

        return servers;
    }

    public void sendTCP(PingRequest pingRequest) {
        this.client.sendTCP(pingRequest);
    }

    @Override
    public void connected(Connection connection) {

        System.out.println("[Client] Verbunden!");

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
        }else if(object instanceof ServerInformation){
            ServerInformation serverInformation = (ServerInformation) object;

            System.out.println("[Client] "+connection.getRemoteAddressUDP().getAddress()+":"+serverInformation.getTcpPort());
            return;
        }else if(object instanceof JoinedPlayers){
            GameOfLife.players = new ArrayList<>(((JoinedPlayers) object).getPlayers().values());
            return;
        }
    }
}
