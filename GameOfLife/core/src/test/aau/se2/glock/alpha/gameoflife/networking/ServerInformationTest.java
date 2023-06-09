package aau.se2.glock.alpha.gameoflife.networking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

import aau.se2.glock.alpha.gameoflife.networking.packages.ServerInformation;

public class ServerInformationTest {
    @Test
    public void testDefaultConstructor() {
        ServerInformation serverInfo = new ServerInformation();
        assertNull(serverInfo.getHostname());
        assertEquals(0, serverInfo.getTcpPort());
        assertNull(serverInfo.getAddress());
    }

    @Test
    public void testParameterizedConstructor() {
        ServerInformation serverInfo = new ServerInformation("localhost", 8080);
        assertEquals("localhost", serverInfo.getHostname());
        assertEquals(8080, serverInfo.getTcpPort());
        assertNull(serverInfo.getAddress());
    }

    @Test
    public void testGettersAndSetters() throws UnknownHostException {
        InetAddress address = InetAddress.getByName("127.0.0.1");
        ServerInformation serverInfo = new ServerInformation("localhost", 8080);

        serverInfo.setAddress(address);
        assertEquals(address, serverInfo.getAddress());
    }

    @Test
    public void testEqualsAndHashCode() throws UnknownHostException {
        InetAddress address1 = InetAddress.getByName("127.0.0.1");
        InetAddress address2 = InetAddress.getByName("192.168.1.1");

        ServerInformation serverInfo1 = new ServerInformation("localhost", 8080);
        ServerInformation serverInfo2 = new ServerInformation("localhost", 8080);
        ServerInformation serverInfo3 = new ServerInformation("remotehost", 9090);

        serverInfo1.setAddress(address1);
        serverInfo2.setAddress(address1);
        serverInfo3.setAddress(address2);

        assertTrue(serverInfo1.equals(serverInfo2));
        assertFalse(serverInfo1.equals(serverInfo3));
    }

    @Test
    public void testToString() throws UnknownHostException {
        InetAddress address = InetAddress.getByName("127.0.0.1");
        ServerInformation serverInfo = new ServerInformation("localhost", 8080);
        serverInfo.setAddress(address);
        String expected = "ServerInformation{hostname='localhost', address=/127.0.0.1}";
        assertEquals(expected, serverInfo.toString());
    }
}