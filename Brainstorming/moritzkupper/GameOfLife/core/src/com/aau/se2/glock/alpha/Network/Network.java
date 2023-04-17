package com.aau.se2.glock.alpha.Network;

import com.esotericsoftware.kryo.Kryo;

public class Network {
    public static final int TCP_PORT = 54555;
    public static final int UDP_PORT = 54777;

    // Register the classes that will be sent over the network
    public static void registerClasses(Kryo kryo) {
        kryo.register(StringMessage.class);
    }

    public static class StringMessage {
        public String text;
    }
}
