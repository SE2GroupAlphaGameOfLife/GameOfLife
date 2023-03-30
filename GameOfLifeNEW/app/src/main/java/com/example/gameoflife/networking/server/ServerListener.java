package com.example.gameoflife.networking.server;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.example.gameoflife.networking.packages.PingRequest;
import com.example.gameoflife.networking.packages.PingResponse;


public class ServerListener extends Listener {

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

        if ( object instanceof PingRequest) {

            PingResponse pingResponse = new PingResponse();

            connection.sendTCP(pingResponse);

        }

    }

}

