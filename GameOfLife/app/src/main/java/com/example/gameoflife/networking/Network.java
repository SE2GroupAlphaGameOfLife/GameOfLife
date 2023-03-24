package com.example.gameoflife.networking;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {

    public static final int TCP_PORT = 54555;
    public static final int UDP_PORT = 54777;

    // IP address localhost for testing, can be changed later
    public static final String IP_ADDRESS = "127.0.0.1";

    public static void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();


    }

}
