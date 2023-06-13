package aau.se2.glock.alpha.gameoflife.networking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.Player;
import aau.se2.glock.alpha.gameoflife.networking.client.ClientClass;
import aau.se2.glock.alpha.gameoflife.networking.observer.ClientObserver;
import aau.se2.glock.alpha.gameoflife.networking.packages.CheatingMessage;
import aau.se2.glock.alpha.gameoflife.networking.packages.JoinedPlayers;
import aau.se2.glock.alpha.gameoflife.networking.packages.ReportPlayerMessage;
import aau.se2.glock.alpha.gameoflife.networking.packages.ServerInformation;
import aau.se2.glock.alpha.gameoflife.screens.JoinGameScreen;
import aau.se2.glock.alpha.gameoflife.screens.StartGameScreen;

public class ClientClassTest {
    @Mock
    private Client mockClient;

    @Mock
    private Kryo mockKryo;

    @Mock
    private Connection mockConnection;

    private ClientClass clientUnderTest;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockClient.getKryo()).thenReturn(mockKryo);
        clientUnderTest = new ClientClass(mockClient);
        GameOfLife.self = new Player("TestUser", false);
        GameOfLife.availableServers = new ArrayList<>();

        // Mock the GameOfLife instance
        GameOfLife gameOfLifeMock = mock(GameOfLife.class);
        JoinGameScreen joinGameScreenMock = mock(JoinGameScreen.class);
        when(gameOfLifeMock.getScreen()).thenReturn(joinGameScreenMock);
        /*when(joinGameScreenMock.getClass()).thenReturn(joinGameScreenMock.getClass());
         */
        gameOfLifeMock.setScreen(joinGameScreenMock);
        GameOfLife.changeInstance(gameOfLifeMock);
    }

    @Test
    public void testConnect() throws IOException {
        InetAddress testAddress = InetAddress.getByName("127.0.0.1");
        clientUnderTest.connect(testAddress, GameOfLife.TCPPORT, GameOfLife.UDPPORT);

        verify(mockClient).connect(5000, testAddress, GameOfLife.TCPPORT, GameOfLife.UDPPORT);
    }

    @Test
    public void testConnectWithString() throws IOException {
        String testAddress = "127.0.0.1";
        clientUnderTest.connect(testAddress, GameOfLife.TCPPORT, GameOfLife.UDPPORT);

        verify(mockClient).connect(5000, testAddress, GameOfLife.TCPPORT, GameOfLife.UDPPORT);
    }

    @Test
    public void testDisconnect() {
        clientUnderTest.disconnect();

        verify(mockClient).close();
    }

    @Test
    public void testSendPlayerTCP() {
        Player player = new Player("John Doe", true);
        clientUnderTest.sendPlayerTCP(player);

        verify(mockClient).sendTCP(player);
    }

    @Test
    public void testReceived() throws UnknownHostException {
        Connection mockConnection = mock(Connection.class);
        InetAddress testAddress = InetAddress.getByName("127.0.0.1");
        ServerInformation serverInformation = new ServerInformation("Test Server", GameOfLife.TCPPORT);
        serverInformation.setAddress(testAddress);

        clientUnderTest.received(mockConnection, serverInformation);
    }

    @Test
    public void testSendMessageToServerTCP() {
        String message = "Hello Server";
        clientUnderTest.sendMessageToServerTCP(message);
        verify(mockClient).sendTCP(message);
    }

    @Test
    public void testConnected() {
        GameOfLife mockGameOfLife = mock(GameOfLife.class);

        StartGameScreen mockStartGameScreen = Mockito.mock(StartGameScreen.class);

        when(mockGameOfLife.getScreen()).thenReturn(mockStartGameScreen);

        GameOfLife.setInstance(mockGameOfLife);
        System.out.println(GameOfLife.getInstance().getScreen().getClass());
        clientUnderTest.connected(mockConnection);
        verify(mockClient, times(1)).sendTCP(GameOfLife.self);
    }

    @Test
    public void testDisconnected() {
        clientUnderTest.disconnected(mockConnection);
        verify(mockConnection, times(1)).close();
    }

    @Test
    public void testReceivedWithJoinedPlayers() {
        JoinedPlayers joinedPlayers = new JoinedPlayers();
        joinedPlayers.addPlayer(GameOfLife.self, mockConnection.getID());

        clientUnderTest.received(mockConnection, joinedPlayers);

        // Verify that the GameOfLife.players list has been updated
        assertTrue(GameOfLife.players.contains(GameOfLife.self));
    }

    @Test
    public void testReceivedWithString() {
        String startGamePayload = GameOfLife.START_GAME_PAYLOAD;
        clientUnderTest.received(mockConnection, startGamePayload);

        // Verify that the GameOfLife.gameStarted flag has been set to true
        assertTrue(GameOfLife.gameStarted);
    }

    @Test
    public void testClientObservation() {
        ClientObserver mockObserver = mock(ClientObserver.class);
        String payload = "payload";

        clientUnderTest.registerObserver(mockObserver);
        clientUnderTest.notifyObservers(payload);

        verify(mockObserver, times(1)).update(payload);

        clientUnderTest.removeObserver(mockObserver);
        clientUnderTest.notifyObservers(payload);

        verify(mockObserver, times(1)).update(anyString()); //should still be one, because observer was removed
    }

    @Test
    public void testDiscoverServers() {
        clientUnderTest.discoverServers(GameOfLife.UDPPORT);
        verify(mockClient).discoverHosts(GameOfLife.UDPPORT, 3000);
    }

    /*
    @Test
    public void testSendReportPlayerTCP() {
        Player player = new Player("John Doe", true);
        clientUnderTest.sendReportPlayerTCP(player);
        verify(mockClient).sendTCP(any(ReportPlayerMessage.class));
    }

    @Test
    public void testSendPlayerCheatedTCP() {
        Player player = new Player("John Doe", true);
        int cheatedAmount = 50;
        clientUnderTest.sendPlayerCheatedTCP(player, cheatedAmount);
        verify(mockClient).sendTCP(any(CheatingMessage.class));
    }*/

    @Test
    public void testSendPlayerCheatedTCP() {
        Player mockPlayer = mock(Player.class);
        int cheatedAmount = 5;

        when(mockPlayer.getId()).thenReturn(1);
        when(mockPlayer.getAge()).thenReturn(10);

        clientUnderTest.sendPlayerCheatedTCP(mockPlayer, cheatedAmount);

        ArgumentCaptor<CheatingMessage> argumentCaptor = ArgumentCaptor.forClass(CheatingMessage.class);
        verify(mockClient).sendTCP(argumentCaptor.capture());
        CheatingMessage sentMessage = argumentCaptor.getValue();
        assertEquals("1#10#5", sentMessage.getPayload());
    }

    @Test
    public void testSendReportPlayerTCP() {
        Player mockPlayer = mock(Player.class);

        when(mockPlayer.getId()).thenReturn(1);

        // When
        clientUnderTest.sendReportPlayerTCP(mockPlayer);

        // Then
        ArgumentCaptor<ReportPlayerMessage> argumentCaptor = ArgumentCaptor.forClass(ReportPlayerMessage.class);
        verify(mockClient).sendTCP(argumentCaptor.capture());
        ReportPlayerMessage sentMessage = argumentCaptor.getValue();
        assertEquals("1", sentMessage.getPayload());
    }


    @Test
    public void testGetClient() {
        assertEquals(mockClient, clientUnderTest.getClient());
    }

    @Test
    public void testObserverSubjectMethods() {
        ClientObserver mockObserver = mock(ClientObserver.class);
        clientUnderTest.registerObserver(mockObserver);
        assertTrue(clientUnderTest.getClientObservers().contains(mockObserver));
        clientUnderTest.removeObserver(mockObserver);
        assertFalse(clientUnderTest.getClientObservers().contains(mockObserver));
    }


}