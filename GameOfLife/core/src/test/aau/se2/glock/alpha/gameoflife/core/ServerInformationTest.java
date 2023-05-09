package aau.se2.glock.alpha.gameoflife.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.net.InetAddress;

import aau.se2.glock.alpha.gameoflife.networking.packages.ServerInformation;

public class ServerInformationTest {
    @Test
    public void testGetTcpPort() {
        ServerInformation server = new ServerInformation("test", 1234);
        assertEquals(1234, server.getTcpPort());
    }

    @Test
    public void testGetHostname() {
        ServerInformation server = new ServerInformation("test", 1234);
        assertEquals("test", server.getHostname());
    }

    @Test
    public void testGetAddress() {
        ServerInformation server = new ServerInformation("test", 1234);
        assertNull(server.getAddress());
    }

    @Test
    public void testSetAddress() throws Exception {
        ServerInformation server = new ServerInformation("test", 1234);
        InetAddress address = InetAddress.getByName("127.0.0.1");
        server.setAddress(address);
        assertEquals(address, server.getAddress());
    }

    @Test
    public void testEquals() throws Exception {
        ServerInformation server1 = new ServerInformation("test", 1234);
        InetAddress address1 = InetAddress.getByName("127.0.0.1");
        server1.setAddress(address1);

        ServerInformation server2 = new ServerInformation("test", 1234);
        InetAddress address2 = InetAddress.getByName("127.0.0.1");
        server2.setAddress(address2);

        assertTrue(server1.equals(server2));
    }

    @Test
    public void testToString() throws Exception {
        ServerInformation server = new ServerInformation("test", 1234);
        InetAddress address = InetAddress.getByName("127.0.0.1");
        server.setAddress(address);
        assertEquals("ServerInformation{hostname='test', address=/127.0.0.1}", server.toString());
    }
}
