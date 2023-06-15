package aau.se2.glock.alpha.gameoflife.networking.packages;

import aau.se2.glock.alpha.gameoflife.GameOfLife;
import aau.se2.glock.alpha.gameoflife.core.logic.PlayerCheated;
import aau.se2.glock.alpha.gameoflife.networking.server.ServerClass;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.MockitoAnnotations;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CheatingVisitorTest {

    private CheatingVisitor cheatingVisitor;
    private ServerClass server;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        server = mock(ServerClass.class);
        cheatingVisitor = new CheatingVisitor();
        GameOfLife.server = this.server;
    }

    @Test
    public void testVisitReportPlayerMessage() {
        ReportPlayerMessage reportPlayerMessage = new ReportPlayerMessage("Test Payload");
        cheatingVisitor.visit(reportPlayerMessage);
        // As there is no implementation in visit method for ReportPlayerMessage, we can't verify any changes.
        // Please add more assertions if you implement some logic in this method.
    }

    @Test
    public void testVisitCheatingMessage() {
        List<PlayerCheated> playerCheatedList = mock(List.class);
        when(server.getPlayerCheatedList()).thenReturn(playerCheatedList);

        CheatingMessage cheatingMessage = new CheatingMessage("1#25#2");
        cheatingVisitor.visit(cheatingMessage);

        PlayerCheated expectedPlayerCheated = new PlayerCheated(1, 25, 2);
        verify(playerCheatedList).add(argThat(new ArgumentMatcher<PlayerCheated>() {
            @Override
            public boolean matches(PlayerCheated argument) {
                return argument.getCheatedAtAge() == expectedPlayerCheated.getCheatedAtAge()
                        && argument.getPlayerId() == expectedPlayerCheated.getPlayerId()
                        && argument.getAmountCheated() == expectedPlayerCheated.getAmountCheated();
            }
        }));
    }
}
