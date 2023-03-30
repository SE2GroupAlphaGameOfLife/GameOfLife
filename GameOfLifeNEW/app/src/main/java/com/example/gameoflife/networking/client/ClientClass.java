package com.example.gameoflife.networking.client;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.example.gameoflife.networking.packages.PingRequest;
import com.example.gameoflife.networking.packages.PingResponse;

import java.io.IOException;

public class ClientClass {

    public void startClient(){
        Client client = new Client();

        client.start();

        try {
            client.connect(5000, "127.0.0.1", 25444);
        } catch (IOException e) {
            e.printStackTrace();
        }

        client.addListener(new ClientListener());

        Kryo kryo = client.getKryo();
        kryo.register(PingRequest.class);
        kryo.register(PingResponse.class);

        PingRequest pingRequest = new PingRequest();

        client.sendTCP(pingRequest);

    }
}
