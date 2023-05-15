package aau.se2.glock.alpha.gameoflife.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import aau.se2.glock.alpha.gameoflife.GameOfLife;

public class PlayerTest {

    @Mock
    GameOfLife gameOfLife;
    @Mock
    Board board;

    Player player;

    @Before
    public void setup(){
    }

    @Test
    public void testPlayer(){
        player = new Player("Username", true);

        player.rollTheDice();

        assertTrue(player.getMoveCount() > 0 && player.getMoveCount() < 10);
    }

    @Test
    public void testPlayerMakeMove(){
        player = new Player("Username", true);

        player.rollTheDice();

        player.getMoveCount();

        player.makeMove();

        while(player.getMoveCount() > 0){
            player.chooseDirection(0);
            player.makeMove();
        }

        assertEquals(0,player.getMoveCount());
    }
}
