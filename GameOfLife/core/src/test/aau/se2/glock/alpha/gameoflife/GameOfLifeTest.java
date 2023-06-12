package aau.se2.glock.alpha.gameoflife;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import aau.se2.glock.alpha.gameoflife.core.utilities.ProximitySensorInterface;
import aau.se2.glock.alpha.gameoflife.networking.client.ClientClass;

public class GameOfLifeTest {

    @Mock
    private ProximitySensorInterface mockProximitySensorInterface;
    @Mock
    private static ClientClass mockClientClass;
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
