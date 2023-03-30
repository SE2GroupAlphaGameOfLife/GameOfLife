package com.example.gameoflife.networking.client;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.example.gameoflife.networking.packages.PingResponse;

public class ClientListener extends Listener {

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

        if ( object instanceof PingResponse) {

            PingResponse pingResponse = (PingResponse) object;

            System.out.println("Time: " + pingResponse.time);

        }

    }

}

