package aau.se2.glock.alpha.gameoflife.networking;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import aau.se2.glock.alpha.gameoflife.core.Player;
import aau.se2.glock.alpha.gameoflife.networking.packages.JoinedPlayers;

public class JoinedPlayersTest{

    private JoinedPlayers joinedPlayers;
    private HashMap<Integer, Player> oldList;
    private Player p1, p2;
    private int pId1, pId2;

    @Before
    public void setup() {
        pId1 = 1;
        pId2 = 2;
        p1 = new Player();
        p1.setId(pId1);
        p1.setOnline(true);
        p2 = new Player();
        p2.setId(pId2);
        p2.setOnline(true);
        joinedPlayers = new JoinedPlayers();
        oldList = new HashMap<Integer, Player>();
    }

    @Test
    public void addNewPlayerTest(){
        assertTrue(joinedPlayers.addPlayer(p1, pId1));
    }

    @Test
    public void addExistingPlayerTest(){
        assertTrue(joinedPlayers.addPlayer(p1, pId1));
        assertFalse(joinedPlayers.addPlayer(p1, pId1));
    }

    @Test
    public void removeExistingPlayerWithConnectionIDTest(){
        assertTrue(joinedPlayers.addPlayer(p1, pId1));
        assertTrue(joinedPlayers.addPlayer(p2, pId2));
        assertTrue(joinedPlayers.removePlayerWithConnectionID(pId1));
        assertFalse(joinedPlayers.getPlayers().containsKey(pId1));
        assertTrue(joinedPlayers.getPlayers().containsKey(pId2));
    }

    @Test
    public void removeNewPlayerWithConnectionIDTest(){
        assertTrue(joinedPlayers.addPlayer(p1, pId1));
        assertFalse(joinedPlayers.removePlayerWithConnectionID(pId2));
    }

    @Test
    public void getPlayersTest(){
        oldList.put(pId1, p1);
        oldList.put(pId2, p2);

        assertTrue(joinedPlayers.addPlayer(p1, pId1));
        assertTrue(joinedPlayers.addPlayer(p2, pId2));

        assertTrue(joinedPlayers.getPlayers().equals(oldList));
    }

    @Test
    public void getPlayerCountTest(){
        oldList.put(pId1, p1);
        oldList.put(pId2, p2);

        assertTrue(joinedPlayers.addPlayer(p1, pId1));
        assertTrue(joinedPlayers.addPlayer(p2, pId2));

        assertTrue(joinedPlayers.getPlayers().size() == oldList.size());
        assertTrue(joinedPlayers.getPlayerCount() == joinedPlayers.getPlayers().size());
    }

    @Test
    public void setPlayersTurnTest(){
        p1.setHasTurn(false);
        p2.setHasTurn(false);
        Player p3 = new Player();
        p3.setHasTurn(true);
        int pId3 = pId2+1;
        p3.setId(pId3);
        p3.setOnline(true);

        assertTrue(joinedPlayers.addPlayer(p1, pId1));
        assertTrue(joinedPlayers.addPlayer(p2, pId2));
        assertTrue(joinedPlayers.addPlayer(p3, pId3));

        joinedPlayers.setPlayersTurn(pId1);
        assertTrue(joinedPlayers.getPlayers().get(pId1).hasTurn());
        assertFalse(joinedPlayers.getPlayers().get(pId2).hasTurn());
        assertFalse(joinedPlayers.getPlayers().get(pId3).hasTurn());
        joinedPlayers.setPlayersTurn(pId2);
        assertTrue(joinedPlayers.getPlayers().get(pId2).hasTurn());
        assertFalse(joinedPlayers.getPlayers().get(pId1).hasTurn());
        assertFalse(joinedPlayers.getPlayers().get(pId3).hasTurn());
        joinedPlayers.setPlayersTurn(pId3);
        assertTrue(joinedPlayers.getPlayers().get(pId3).hasTurn());
        assertFalse(joinedPlayers.getPlayers().get(pId1).hasTurn());
        assertFalse(joinedPlayers.getPlayers().get(pId2).hasTurn());
        joinedPlayers.setPlayersTurn(pId3+1);
        assertTrue(joinedPlayers.getPlayers().get(pId1).hasTurn());
        assertFalse(joinedPlayers.getPlayers().get(pId2).hasTurn());
        assertFalse(joinedPlayers.getPlayers().get(pId3).hasTurn());

        p3.setHasTurn(true);
        p3.setOnline(false);
        joinedPlayers.setPlayersTurn(pId3);
        assertTrue(joinedPlayers.getPlayers().get(pId1).hasTurn());
        assertFalse(joinedPlayers.getPlayers().get(pId2).hasTurn());
        assertFalse(joinedPlayers.getPlayers().get(pId3).hasTurn());
    }
}