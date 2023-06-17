package aau.se2.glock.alpha.gameoflife.networking.packages;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.Player;
import aau.se2.glock.alpha.gameoflife.core.logic.PlayerCheated;
import aau.se2.glock.alpha.gameoflife.networking.server.ServerClass;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ReportPlayerVisitorTest {

    private ReportPlayerVisitor reportPlayerVisitor;
    private ServerClass server;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        server = mock(ServerClass.class);
        reportPlayerVisitor = new ReportPlayerVisitor();
        GameOfLife.server = this.server; // assuming server is a public static field in GameOfLife
    }

    @Test
    public void testVisitReportPlayerMessage() {
        List<Player> players = new ArrayList<>();
        Player player1 = new Player();
        player1.setId(1);
        player1.setAge(25);  // Assuming setAge() sets player's age
        players.add(player1);

        Player player2 = new Player();
        player2.setId(2);
        players.add(player2);

        GameOfLife.players = players;

        List<PlayerCheated> playerCheatedList = new ArrayList<>();
        playerCheatedList.add(new PlayerCheated(21, 1, 3)); // Player 1 cheated 4 rounds ago

        when(server.getPlayerCheatedList()).thenReturn(playerCheatedList);

        ReportPlayerMessage reportPlayerMessage = new ReportPlayerMessage("1");
        reportPlayerVisitor.visit(reportPlayerMessage);

        assertEquals(1, player1.getId());
        assertEquals(2, player2.getId());
        assertEquals(-3, player1.getPosition());
    }


    @Test
    public void testVisitCheatingMessage() {
        CheatingMessage cheatingMessage = new CheatingMessage("Test Payload");
        reportPlayerVisitor.visit(cheatingMessage);
        // As there is no implementation in visit method for CheatingMessage, we can't verify any changes.
        // Please add more assertions if you implement some logic in this method.
    }
}
