package com.aau.se2.glock.alpha.Network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;

public class GameClient {
    private Client client;

    public GameClient(String ipAddress) {
        client = new Client();
        Network.registerClasses(client.getKryo());

        client.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof Network.StringMessage) {
                    Network.StringMessage message = (Network.StringMessage) object;
                    System.out.println("Received message: " + message.text);
                }
            }
        });

        client.start();
        try {
            client.connect(5000, ipAddress, Network.TCP_PORT, Network.UDP_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String text) {
        Network.StringMessage message = new Network.StringMessage();
        message.text = text;
        client.sendTCP(message);
    }
}
