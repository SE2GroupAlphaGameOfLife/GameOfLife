package com.mygdx.gameoflife.test.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.gameoflife.src.core.Player;

public class PlayerTest {
    private Player player;

    @Before
    public void setUp() {
        player = new Player("testUser", true);
    }

    @Test
    public void testRollTheDice() {
        int roll = player.rollTheDice();
        assertTrue(roll >= 1 && roll <= 10);
    }

    @Test
    public void testChooseDirection() {
        player.setMoveCount(1);
        player.chooseDirection(0);
        assertEquals(1, player.getPosition());
    }

    @Test
    public void testMakeMove() {
        player.setMoveCount(1);
        assertTrue(player.makeMove());
        assertEquals(1, player.getPosition());
    }

    @Test
    public void testGetters() {
        assertEquals("testUser", player.getUsername());
        assertEquals(0, player.getPosition());
    }
}
