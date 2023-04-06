package com.aau.se2.glock.alpha.Network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class GameServer {
    private Server server;

    public GameServer() {
        server = new Server();
        Network.registerClasses(server.getKryo());

        server.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof Network.StringMessage) {
                    Network.StringMessage message = (Network.StringMessage) object;
                    System.out.println("Received message: " + message.text);

                    // Send the received message back to all clients
                    server.sendToAllTCP(message);
                }
            }
        });

        try {
            server.bind(Network.TCP_PORT, Network.UDP_PORT);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
