package aau.se2.glock.alpha.gameoflife;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import aau.se2.glock.alpha.gameoflife.core.utilities.ProximitySensorInterface;
import aau.se2.glock.alpha.gameoflife.networking.client.ClientClass;

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
}
