package aau.se2.glock.alpha.gameoflife.networking;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.ServerDiscoveryHandler;
import com.esotericsoftware.kryonet.serialization.Serialization;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.verification.Times;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.Player;
import aau.se2.glock.alpha.gameoflife.networking.client.ClientClass;
import aau.se2.glock.alpha.gameoflife.networking.packages.JoinedPlayers;
import aau.se2.glock.alpha.gameoflife.networking.server.ServerClass;

public class ServerClassTest {

    @Mock
    private Server mockServer;

    @Mock
    private Kryo mockKryo;

    @Mock
    private Connection mockConnection;

    @Mock
    private DatagramChannel mockDatagramChannel;

    @Mock
    private InetSocketAddress mockFromAddress;

    @Mock
    private Client mockClient; // Mock KryoNet client.

    private ServerClass serverUnderTest;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockServer.getKryo()).thenReturn(mockKryo);
        GameOfLife.client = mock(ClientClass.class); // Add this line
        when(GameOfLife.client.getClient()).thenReturn(mockClient); // Return mockClient when getClient() is invoked.

        // Create mock Serialization
        Serialization mockSerialization = mock(Serialization.class);
        // When getSerialization() is invoked on the mockClient, return the mockSerialization.
        when(mockClient.getSerialization()).thenReturn(mockSerialization);

        serverUnderTest = new TestServerClass(mockClient); // Pass the mockClient to the constructor of your TestServerClass
        serverUnderTest.setServer(mockServer);
        GameOfLife.gameStarted = false;
        GameOfLife.players = new ArrayList<Player>();
    }
    @Test
    public void testStartServer() {
        serverUnderTest.start("localhost");
        assertTrue(serverUnderTest.isServerStarted());
    }

    @Test
    public void testCloseServer() {
        serverUnderTest.close();
        assertFalse(serverUnderTest.isServerStarted());
    }

    @Test
    public void testSendPlayersObjectToAll() {
        JoinedPlayers joinedPlayers = new JoinedPlayers();
        serverUnderTest.setPlayers(joinedPlayers);

        serverUnderTest.sendPlayersObjectToAll();
        verify(mockServer, times(1)).sendToAllTCP(joinedPlayers);
    }

    @Test
    public void testSendMessageToAll() {
        String message = "Test Message";
        serverUnderTest.sendMessageToAll(message);
        verify(mockServer, times(1)).sendToAllTCP(message);
    }

    @Test
    public void testOnDiscoverHost() throws IOException {
        boolean result = serverUnderTest.getServerDiscoveryHandler().onDiscoverHost(mockDatagramChannel, mockFromAddress);
        assertTrue(result);
    }

    @Test
    public void testConnected() {
        GameOfLife.gameStarted = true;
        Player mockPlayer = mock(Player.class);
        GameOfLife.players.add(mockPlayer);

        JoinedPlayers j = new JoinedPlayers();
        j.addPlayer(mockPlayer, mockConnection.getID());
        serverUnderTest.setPlayers(j);

        serverUnderTest.connected(mockConnection);

        ArgumentCaptor<JoinedPlayers> argument = ArgumentCaptor.forClass(JoinedPlayers.class);
        verify(mockServer, times(1)).sendToAllTCP(argument.capture());
        assertEquals(mockPlayer, argument.getValue().getPlayers().get(mockConnection.getID()));
    }


    @Test
    public void testDisconnected() {
        GameOfLife.gameStarted = true;
        Player mockPlayer = mock(Player.class);
        GameOfLife.players.add(mockPlayer);

        JoinedPlayers j = new JoinedPlayers();
        j.addPlayer(mockPlayer, mockConnection.getID());
        serverUnderTest.setPlayers(j);

        serverUnderTest.disconnected(mockConnection);

        ArgumentCaptor<JoinedPlayers> argument = ArgumentCaptor.forClass(JoinedPlayers.class);
        verify(mockServer, times(1)).sendToAllTCP(argument.capture());
        assertEquals(mockPlayer, argument.getValue().getPlayers().get(mockConnection.getID()));
    }

    @Test
    public void testDisconnectedOnJoinGameScreen() {
        GameOfLife.gameStarted = false;
        Player mockPlayer = mock(Player.class);
        GameOfLife.players.add(mockPlayer);

        JoinedPlayers j = new JoinedPlayers();
        j.addPlayer(mockPlayer, mockConnection.getID());
        serverUnderTest.setPlayers(j);

        serverUnderTest.disconnected(mockConnection);

        ArgumentCaptor<JoinedPlayers> argument = ArgumentCaptor.forClass(JoinedPlayers.class);
        verify(mockServer, times(1)).sendToAllTCP(argument.capture());
        assertNotEquals(mockPlayer, argument.getValue().getPlayers().get(mockConnection.getID()));
    }

    @Test
    public void testReceivedWithPlayer() {
        Player mockPlayer = mock(Player.class);
        JoinedPlayers j = new JoinedPlayers();
        j.addPlayer(mockPlayer, mockConnection.getID());
        serverUnderTest.setPlayers(j);

        serverUnderTest.received(mockConnection, mockPlayer);
        ArgumentCaptor<JoinedPlayers> argument = ArgumentCaptor.forClass(JoinedPlayers.class);
        verify(mockServer, times(1)).sendToAllTCP(argument.capture());
        assertEquals(mockPlayer, argument.getValue().getPlayers().get(mockConnection.getID()));
    }

    @Test
    public void testReceivedWithString() {
        String startGamePayload = GameOfLife.START_GAME_PAYLOAD;
        serverUnderTest.received(mockConnection, startGamePayload);
        verify(mockServer, times(1)).sendToAllTCP(startGamePayload);
    }

    @Test
    public void testReceivedWithOtherObject() {
        Object mockObject = new Object();
        serverUnderTest.received(mockConnection, mockObject);
        verify(mockServer, times(1)).getKryo();
        verify(mockServer, times(1)).setDiscoveryHandler(serverUnderTest.getServerDiscoveryHandler());
    }

    public class TestServerClass extends ServerClass {
        private Client mockClient;

        public TestServerClass(Client mockClient) {
            super(2000, 3000, mockServer);
            this.mockClient = mockClient;
        }

        @Override
        protected Client getClient() {
            return mockClient;
        }
    }
}
