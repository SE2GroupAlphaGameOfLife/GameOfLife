package aau.se2.glock.alpha.gameoflife.networking;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import aau.se2.glock.alpha.gameoflife.networking.client.ClientClassTest;
import aau.se2.glock.alpha.gameoflife.networking.packages.CheatingMessageTest;
import aau.se2.glock.alpha.gameoflife.networking.packages.CheatingVisitorTest;
import aau.se2.glock.alpha.gameoflife.networking.packages.JoinedPlayersTest;
import aau.se2.glock.alpha.gameoflife.networking.packages.ReportPlayerMessageTest;
import aau.se2.glock.alpha.gameoflife.networking.packages.ReportPlayerVisitorTest;
import aau.se2.glock.alpha.gameoflife.networking.packages.ServerInformationTest;
import aau.se2.glock.alpha.gameoflife.networking.server.ServerClassTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ClientClassTest.class,
        CheatingMessageTest.class,
        CheatingVisitorTest.class,
        JoinedPlayersTest.class,
        ReportPlayerMessageTest.class,
        ReportPlayerVisitorTest.class,
        ServerInformationTest.class,
        ServerClassTest.class
})
public class NetworkTest {
}
