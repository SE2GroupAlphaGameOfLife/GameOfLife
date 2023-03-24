package com.example.gameoflife.networking;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import java.io.IOException;

public class Client {

    public static void establishConnection(int tcpPort, int udpPort, int refreshRate, String ipAddress) {

        com.esotericsoftware.kryonet.Client client = new com.esotericsoftware.kryonet.Client();
        client.start();

        try {
            client.connect(refreshRate, ipAddress, tcpPort, udpPort);

            android.util.Log.d("Connection", "Connection established");

            listen(client);
        } catch (IOException e) {
            android.util.Log.e("Connection error", "Connection not established");
            throw new RuntimeException(e);
        }

    }

    public static void listen(com.esotericsoftware.kryonet.Client client) {
        client.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object o) {
                super.received(connection, o);
                Log.set(Log.LEVEL_INFO);
            }
        });
    }


}
