package aau.se2.glock.alpha.gameoflife.core.logic;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class PlayerCheatedTest {

    private PlayerCheated playerCheated;

    @Before
    public void setUp() {
        playerCheated = new PlayerCheated(1, 10, 1000);
    }

    @Test
    public void testGetCheatedAtAge() {
        assertEquals(10, playerCheated.getCheatedAtAge());
    }

    @Test
    public void testGetPlayerId() {
        assertEquals(1, playerCheated.getPlayerId());
    }

    @Test
    public void testGetAmountCheated() {
        assertEquals(1000, playerCheated.getAmountCheated());
    }

    @Test
    public void testToString() {
        assertEquals("PlayerCheated{cheatedAtAge=10, playerId=1, amountCheated=1000}", playerCheated.toString());
    }
}
