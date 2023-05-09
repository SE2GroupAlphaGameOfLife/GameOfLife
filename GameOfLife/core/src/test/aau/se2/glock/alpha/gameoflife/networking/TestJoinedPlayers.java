package aau.se2.glock.alpha.gameoflife.networking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

import aau.se2.glock.alpha.gameoflife.core.Player;
import aau.se2.glock.alpha.gameoflife.networking.packages.JoinedPlayers;

public class TestJoinedPlayers {
    private JoinedPlayers joinedPlayers;
    private InetAddress ip1, ip2;
    private Player player1, player2;

    @Before
    public void setUp() throws UnknownHostException {
        joinedPlayers = new JoinedPlayers();
        ip1 = InetAddress.getByName("192.0.2.1");
        ip2 = InetAddress.getByName("192.0.2.2");
        player1 = Mockito.mock(Player.class);
        player2 = Mockito.mock(Player.class);
    }

    @Test
    public void testAddPlayer() {
        assertTrue(joinedPlayers.addPlayer(player1, ip1));
        assertEquals(1, joinedPlayers.getPlayerCount());
    }

    @Test
    public void testAddPlayer_duplicate() {
        joinedPlayers.addPlayer(player1, ip1);
        assertFalse(joinedPlayers.addPlayer(player2, ip1));
        assertEquals(1, joinedPlayers.getPlayerCount());
    }

    @Test
    public void testRemovePlayerWithIP() {
        joinedPlayers.addPlayer(player1, ip1);
        joinedPlayers.removePlayerWithIP(ip1);
        assertEquals(0, joinedPlayers.getPlayerCount());
    }

    @Test
    public void testSetPlayers() {
        HashMap<InetAddress, Player> players = new HashMap<>();
        players.put(ip1, player1);
        players.put(ip2, player2);
        joinedPlayers.setPlayers(players);
        assertEquals(2, joinedPlayers.getPlayerCount());
    }

    @Test
    public void testGetPlayers() {
        joinedPlayers.addPlayer(player1, ip1);
        joinedPlayers.addPlayer(player2, ip2);
        HashMap<InetAddress, Player> players = joinedPlayers.getPlayers();
        assertEquals(2, players.size());
        assertTrue(players.containsKey(ip1));
        assertTrue(players.containsKey(ip2));
    }

    @Test
    public void testGetPlayerCount() {
        joinedPlayers.addPlayer(player1, ip1);
        assertEquals(1, joinedPlayers.getPlayerCount());
        joinedPlayers.addPlayer(player2, ip2);
        assertEquals(2, joinedPlayers.getPlayerCount());
    }

    @Test
    public void testSetPlayersTurn() {
        Mockito.when(player1.getId()).thenReturn(1);
        Mockito.when(player2.getId()).thenReturn(2);
        joinedPlayers.addPlayer(player1, ip1);
        joinedPlayers.addPlayer(player2, ip2);

        joinedPlayers.setPlayersTurn(1);
        Mockito.verify(player1).setHasTurn(true);
        Mockito.verify(player2, Mockito.never()).setHasTurn(true);

        joinedPlayers.setPlayersTurn(2);
        Mockito.verify(player2).setHasTurn(true);
    }
}
