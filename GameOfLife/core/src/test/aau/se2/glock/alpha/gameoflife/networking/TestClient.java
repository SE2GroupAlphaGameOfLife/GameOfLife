package aau.se2.glock.alpha.gameoflife.networking;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;

import org.junit.Before;
import org.junit.Test;
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
import aau.se2.glock.alpha.gameoflife.networking.packages.ServerInformation;
import aau.se2.glock.alpha.gameoflife.screens.JoinGameScreen;

public class TestClient {
    @Mock
    private Client mockClient;

    @Mock
    private Kryo mockKryo;

    @Mock
    private Connection mockConnection;

    private ClientClass clientUnderTest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
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
    public void testDiscoverServers() {
        /*clientUnderTest.discoverServers(GameOfLife.UDPPORT);

        verify(mockClient, atLeastOnce()).start();
        verify(mockClient, atLeastOnce()).discoverHosts(GameOfLife.UDPPORT, 5000);*/
    }

    @Test
    public void testSendPlayerTCP() {
        Player player = new Player("John Doe", true);
        clientUnderTest.sendPlayerTCP(player);

        verify(mockClient).sendTCP(player);
    }

    @Test
    public void testReceived() throws UnknownHostException {
        Connection mockConnection = Mockito.mock(Connection.class);
        InetAddress testAddress = InetAddress.getByName("127.0.0.1");
        ServerInformation serverInformation = new ServerInformation("Test Server", GameOfLife.TCPPORT);
        serverInformation.setAddress(testAddress);

        clientUnderTest.received(mockConnection, serverInformation);
    }
}