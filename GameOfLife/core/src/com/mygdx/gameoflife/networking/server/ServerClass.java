package com.mygdx.gameoflife.networking.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.mygdx.gameoflife.networking.packages.PingRequest;
import com.mygdx.gameoflife.networking.packages.PingResponse;
import com.mygdx.gameoflife.networking.packages.ServerInformation;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public class ServerClass extends Listener {

    private final int UDPPORT = 54777;
    private int tcpPort = 0;
    private Server server;
    private boolean serverStarted;

    public ServerClass() {
        this.server = new Server();
        this.server.start();

        this.server.addListener(this);

        Kryo kryo = this.server.getKryo();
        kryo.register(PingRequest.class);
        kryo.register(PingResponse.class);
        kryo.register(ServerInformation.class);

        this.serverStarted = false;
    }

    public void start() {
        if (!this.serverStarted) {
            try {
                ServerSocket s = new ServerSocket(0);
                this.tcpPort = s.getLocalPort();
                s.close();

                this.server.bind(this.tcpPort, UDPPORT);

                this.serverStarted = true;

                this.server.sendToAllUDP(new ServerInformation(this.tcpPort));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close(){
        this.server.close();
        this.serverStarted = false;
    }

    public int getTCPport() {
        return this.tcpPort;
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
                pingResponse.setTcpPort(this.tcpPort);
            }

            connection.sendTCP(pingResponse);

        }
    }
}