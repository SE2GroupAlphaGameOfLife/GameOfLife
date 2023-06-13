package aau.se2.glock.alpha.gameoflife.networking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.kryonet.serialization.Serialization;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.List;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.Player;
import aau.se2.glock.alpha.gameoflife.core.logic.PlayerCheated;
import aau.se2.glock.alpha.gameoflife.networking.client.ClientClass;
import aau.se2.glock.alpha.gameoflife.networking.packages.CheatingVisitor;
import aau.se2.glock.alpha.gameoflife.networking.packages.JoinedPlayers;
import aau.se2.glock.alpha.gameoflife.networking.packages.ReportPlayerVisitor;
import aau.se2.glock.alpha.gameoflife.networking.packages.TcpMessage;
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

        assertEquals(serverUnderTest.getPlayers(), j);

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

    @Test(expected = RuntimeException.class)
    public void testStartServerWithIOException() throws IOException {
        doThrow(new IOException()).when(mockServer).bind(anyInt(), anyInt());
        serverUnderTest.start("localhost");
    }

   /* @Test
    public void testConnectedWhenGameStartedAndPlayerExists() {
        GameOfLife.gameStarted = true;
        Player mockPlayer = mock(Player.class);
        when(mockPlayer.isJoning()).thenReturn(true);
        when(mockPlayer.getId()).thenReturn(1);
        when(mockPlayer.isOnline()).thenReturn(false);
        when(mockPlayer.hasTurn()).thenReturn(true);
        when(mockConnection.getID()).thenReturn(1);

        JoinedPlayers j = new JoinedPlayers();
        j.addPlayer(mockPlayer, mockConnection.getID());
        serverUnderTest.setPlayers(j);

        serverUnderTest.connected(mockConnection);

        ArgumentCaptor<JoinedPlayers> argument = ArgumentCaptor.forClass(JoinedPlayers.class);
        verify(mockServer, times(1)).sendToAllTCP(argument.capture());
        Player capturedPlayer = argument.getValue().getPlayers().get(mockConnection.getID());
        assertTrue(capturedPlayer.isOnline());
        assertFalse(capturedPlayer.isJoning());
        assertFalse(capturedPlayer.hasTurn());
        assertEquals(2, capturedPlayer.getId());
    }*/

    @Test
    public void testConnectedWhenGameStartedAndPlayerDoesNotExist() {
        GameOfLife.gameStarted = true;
        Player mockPlayer = mock(Player.class);
        when(mockPlayer.isJoning()).thenReturn(true);
        when(mockPlayer.getId()).thenReturn(1);
        when(mockPlayer.isOnline()).thenReturn(false);
        when(mockPlayer.hasTurn()).thenReturn(true);
        when(mockConnection.getID()).thenReturn(1);

        JoinedPlayers j = new JoinedPlayers();
        serverUnderTest.setPlayers(j);

        serverUnderTest.connected(mockConnection);

        verify(mockConnection, times(1)).close();
    }

    /*
    @Test
    public void testDisconnectedWhenGameStartedAndPlayerExists() {
        GameOfLife.gameStarted = true;
        Player mockPlayer = mock(Player.class);
        when(mockPlayer.isOnline()).thenReturn(true);
        when(mockPlayer.isJoning()).thenReturn(true);
        when(mockPlayer.getId()).thenReturn(1);
        when(mockPlayer.hasTurn()).thenReturn(true);
        when(mockConnection.getID()).thenReturn(1);

        JoinedPlayers j = new JoinedPlayers();
        j.addPlayer(mockPlayer, mockConnection.getID());
        serverUnderTest.setPlayers(j);

        serverUnderTest.disconnected(mockConnection);

        ArgumentCaptor<JoinedPlayers> argument = ArgumentCaptor.forClass(JoinedPlayers.class);
        verify(mockServer, times(1)).sendToAllTCP(argument.capture());
        Player capturedPlayer = argument.getValue().getPlayers().get(mockConnection.getID());
        assertFalse(capturedPlayer.isOnline());
        assertTrue(capturedPlayer.isJoning());
        assertEquals(2, capturedPlayer.getId());
    }*/

    @Test
    public void testDisconnectedWhenGameNotStartedAndPlayerExists() {
        GameOfLife.gameStarted = false;
        Player mockPlayer = mock(Player.class);
        when(mockConnection.getID()).thenReturn(1);

        JoinedPlayers j = new JoinedPlayers();
        j.addPlayer(mockPlayer, mockConnection.getID());
        serverUnderTest.setPlayers(j);

        serverUnderTest.disconnected(mockConnection);

        verify(mockServer, times(1)).sendToAllTCP(any(JoinedPlayers.class));
    }

    @Test
    public void testDisconnectedWhenGameNotStartedAndPlayerDoesNotExist() {
        GameOfLife.gameStarted = false;
        when(mockConnection.getID()).thenReturn(1);

        JoinedPlayers j = new JoinedPlayers();
        serverUnderTest.setPlayers(j);

        ArgumentCaptor<Object> argumentCaptor = ArgumentCaptor.forClass(Object.class);
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                Object argument = invocation.getArgument(0);
                System.out.println("Captured argument: " + argument);
                System.out.println("Argument class: " + argument.getClass().getName());
                System.out.println("Argument toString: " + argument.toString());
                return null;
            }
        }).when(mockServer).sendToAllTCP(argumentCaptor.capture());

        serverUnderTest.disconnected(mockConnection);

        List<Object> capturedArguments = argumentCaptor.getAllValues();
        // Use the capturedArguments list as needed for assertions or verifications
    }

    @Test
    public void testReceivedWithTcpMessage() {
        TcpMessage mockTcpMessage = mock(TcpMessage.class);

        serverUnderTest.received(mockConnection, mockTcpMessage);

        verify(mockTcpMessage, times(1)).accept(any(ReportPlayerVisitor.class));
        verify(mockTcpMessage, times(1)).accept(any(CheatingVisitor.class));
    }

    @Test
    public void testGetUDPPORT() {
        int expectedUDPPort = 1234;
        serverUnderTest = new ServerClass(2000, expectedUDPPort, mockServer);
        int actualUDPPort = serverUnderTest.getUDPPORT();
        assertEquals(expectedUDPPort, actualUDPPort);
    }

    @Test
    public void testGetTCPPORT() {
        int expectedTCPPort = 5678;
        serverUnderTest = new ServerClass(expectedTCPPort, 3000, mockServer);
        int actualTCPPort = serverUnderTest.getTCPPORT();
        assertEquals(expectedTCPPort, actualTCPPort);
    }

    @Test
    public void testGetServer() {
        Server actualServer = serverUnderTest.getServer();
        assertSame(mockServer, actualServer);
    }

    @Test
    public void testGetPlayerCheatedList() {
        List<PlayerCheated> expectedPlayerCheatedList = new ArrayList<>();
        serverUnderTest.setPlayerCheatedList(expectedPlayerCheatedList);
        List<PlayerCheated> actualPlayerCheatedList = serverUnderTest.getPlayerCheatedList();
        assertEquals(expectedPlayerCheatedList, actualPlayerCheatedList);
    }

    @Test
    public void testSetPlayerCheatedList() {
        List<PlayerCheated> expectedPlayerCheatedList = new ArrayList<>();
        serverUnderTest.setPlayerCheatedList(expectedPlayerCheatedList);
        List<PlayerCheated> actualPlayerCheatedList = serverUnderTest.getPlayerCheatedList();
        assertEquals(expectedPlayerCheatedList, actualPlayerCheatedList);
    }

    @Test
    public void testGetHostname() {
        String expectedHostname = "localhost";
        serverUnderTest.setHostname(expectedHostname);
        String actualHostname = serverUnderTest.getHostname();
        assertEquals(expectedHostname, actualHostname);
    }

    @Test
    public void testSetHostname() {
        String expectedHostname = "localhost";
        serverUnderTest.setHostname(expectedHostname);
        String actualHostname = serverUnderTest.getHostname();
        assertEquals(expectedHostname, actualHostname);
    }

    public class TestServerClass extends ServerClass {
        private final Client mockClient;

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
