package aau.se2.glock.alpha.gameoflife;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.esotericsoftware.kryo.Kryo;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;

import aau.se2.glock.alpha.gameoflife.core.Player;
import aau.se2.glock.alpha.gameoflife.core.jobs.Job;
import aau.se2.glock.alpha.gameoflife.core.utilities.ProximitySensorInterface;
import aau.se2.glock.alpha.gameoflife.networking.client.ClientClass;
import aau.se2.glock.alpha.gameoflife.networking.packages.CheatingMessage;
import aau.se2.glock.alpha.gameoflife.networking.packages.DiscoveryResponsePacket;
import aau.se2.glock.alpha.gameoflife.networking.packages.JoinedPlayers;
import aau.se2.glock.alpha.gameoflife.networking.packages.ReportPlayerMessage;
import aau.se2.glock.alpha.gameoflife.networking.packages.TcpMessage;
import aau.se2.glock.alpha.gameoflife.screens.MainMenuScreen;

public class GameOfLifeTest {

    @Mock
    private static ClientClass mockClientClass;
    @Mock
    private ProximitySensorInterface mockProximitySensorInterface;
    @Mock
    private FreeTypeFontGenerator mockFreeTypeFontGenerator;

    @BeforeClass
    public static void setUpClass() {
        mockClientClass = Mockito.mock(ClientClass.class);
    }

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        GameOfLife gameOfLife = new GameOfLife();
        GameOfLife spiedInstance = Mockito.spy(gameOfLife);

        // Override the setScreen method to do nothing
        doAnswer(invocation -> null).when(spiedInstance).setScreen(any());
        doAnswer(invocation -> null).when(spiedInstance).getNewMainMenuScreen();

        GameOfLife.setInstance(spiedInstance);
        GameOfLife.proximitySensorInterface = mockProximitySensorInterface;
        GameOfLife.client = mockClientClass;
    }

    @Test
    public void testCreate() {
        GameOfLife.getInstance().create();
        verify(GameOfLife.proximitySensorInterface, times(1)).registerSensor();
    }

    @Test
    public void testDispose() {
        GameOfLife.getInstance().dispose();
        verify(GameOfLife.proximitySensorInterface, times(1)).unregisterSensor();
    }

    @Test
    public void testIsGameStarted() {
        GameOfLife.gameStarted = true;
        assertTrue(GameOfLife.isGameStarted());
    }

    @Test
    public void testGetInstance() {
        // Call getInstance() for the first time
        GameOfLife firstInstance = GameOfLife.getInstance();

        // Verify that the instance is not null
        assertNotNull(firstInstance);

        // Verify that the static class variables are initialized
        assertNotNull(GameOfLife.players);
        assertNotNull(GameOfLife.client);
        assertNotNull(GameOfLife.availableServers);

        // Call getInstance() for the second time
        GameOfLife secondInstance = GameOfLife.getInstance();

        // Verify that the same instance is returned
        assertSame(firstInstance, secondInstance);
    }

    @Test
    public void testRegisterClasses() {
        // Arrange
        Kryo mockKryo = Mockito.mock(Kryo.class);

        // Act when isUnitTest is false
        GameOfLife.registerClasses(mockKryo, false);

        // Assert
        verify(mockKryo).register(JoinedPlayers.class);
        verify(mockKryo).register(Color.class);
        verify(mockKryo).register(Player.class);
        verify(mockKryo).register(Job.class);
        verify(mockKryo).register(ArrayList.class);
        verify(mockKryo).register(HashMap.class);
        verify(mockKryo).register(DiscoveryResponsePacket.class);
        verify(mockKryo).register(TcpMessage.class);
        verify(mockKryo).register(ReportPlayerMessage.class);
        verify(mockKryo).register(CheatingMessage.class);
        verify(mockKryo).register(SecureRandom.class);

        // Reset mockKryo
        Mockito.reset(mockKryo);

        // Act when isUnitTest is true
        GameOfLife.registerClasses(mockKryo, true);

        // Assert
        verify(mockKryo).register(JoinedPlayers.class);
        verify(mockKryo).register(Color.class);
        verify(mockKryo).register(Player.class);
        verify(mockKryo).register(Job.class);
        verify(mockKryo).register(ArrayList.class);
        verify(mockKryo).register(HashMap.class);
        verify(mockKryo).register(DiscoveryResponsePacket.class);
        verify(mockKryo).register(TcpMessage.class);
        verify(mockKryo).register(ReportPlayerMessage.class);
        verify(mockKryo).register(CheatingMessage.class);
        verify(mockKryo, times(0)).register(SecureRandom.class);  // SecureRandom.class should not be registered when isUnitTest is true
    }

    @Test
    public void testGetNewMainMenuScreen() {
        MainMenuScreen screen = GameOfLife.getInstance().getNewMainMenuScreen();
        assertNull(screen);
    }
}
